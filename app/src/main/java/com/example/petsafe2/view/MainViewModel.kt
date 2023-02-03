package com.example.petsafe2.view

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.petsafe2.model.Board
import com.example.petsafe2.model.User
import com.example.petsafe2.model.local.LocalDatabase
import com.example.petsafe2.model.network.RetrofitClient
import com.example.petsafe2.model.network.api.BoardListResult
import com.example.petsafe2.model.network.api.BoardService
import com.example.petsafe2.model.network.api.UserRetrofitResult
import com.example.petsafe2.model.network.api.UserService
import com.example.petsafe2.model.repository.BoardRepository
import com.example.petsafe2.model.repository.CommentRepository
import com.example.petsafe2.model.repository.FirebaseRepository
import com.example.petsafe2.model.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application): AndroidViewModel(application) {

    companion object{
        const val TAG = "PetSafe2:: MainViewModel"
    }

    val getBoards: LiveData<List<Board>>

    private val userRepository: UserRepository
    private val boardRepository: BoardRepository
    private val commentRepository: CommentRepository
    private val firebaseRepository: FirebaseRepository

    private var _loginCheck = MutableLiveData<String>()
    val loginCheck: LiveData<String>
        get() = _loginCheck

    private var _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData

    private var _boardList = MutableLiveData<List<Board>>()
    val boardList: LiveData<List<Board>>
        get() = _boardList

    //인스턴스 초기화
    init {
        val userDao = LocalDatabase.getInstance(application)!!.getUserDao()
        val userService = RetrofitClient.getInstance().create(UserService::class.java)
        userRepository = UserRepository(userDao, userService)

        val boardDao = LocalDatabase.getInstance(application)!!.getBoardDao()
        val boardService = RetrofitClient.getInstance().create(BoardService::class.java)
        boardRepository = BoardRepository(boardDao, boardService)

        val commentDao = LocalDatabase.getInstance(application)!!.getCommentDao()
        commentRepository = CommentRepository(commentDao)

        getBoards = boardRepository.getBoards.asLiveData()

        firebaseRepository = FirebaseRepository()
    }

    fun getGoogleSignInClient(context: Context, clientId: String): GoogleSignInClient {
        return firebaseRepository.createGoogleSignInClient(context, clientId)
    }

    fun googleSignAuth(acct: GoogleSignInAccount){
        //viewModel lifecycle에 맞춰서 onClear가 되었을때 Scope도 모두 cancel됨
        //viewModelScope는 기본적으로 Dispatchers.Main에 bound 되어 있음
        viewModelScope.launch{
            val handler = CoroutineExceptionHandler{context, exception -> Log.d(TAG,"googleSignAuth: ErrorContext = $context | ErrorException = [ $exception ]")}
            val scope = CoroutineScope(Job())

            //scope 내에서 일어난 error는 viewModelScope에 영향을 주지 않음(error 발생시 handler에서 catch됨)
            scope.launch(handler) {
                val loginResult = async { firebaseRepository.firebaseAuthWithGoogle(acct) }.await()
                async { loginResult.result.user?.let { firebaseRepository.userCheck(it).await() } }.await()
            }

        }
    }

    /** ID와 Pwd를 입력받아 node.js 서버와 통신
     * @param userId 사용자가 입력한 ID
     * @param userPwd 사용자가 입력한 Password
    * */
    fun getLoginCheck(userId: String, userPwd: String){
        viewModelScope.launch {
            userRepository.getLoginCheck(userId, userPwd).enqueue(object: Callback<UserRetrofitResult> {
                override fun onResponse(call: Call<UserRetrofitResult>, response: Response<UserRetrofitResult>) {
                    _loginCheck.value = response.body()?.message
                    Log.d(TAG, "onResponse: 로그인 여부 = ${response.body()?.message}")
                }

                override fun onFailure(call: Call<UserRetrofitResult>, t: Throwable) {
                    Log.d(TAG, "getLoginCheck: 로그인 실패=$t")
                }
            })
        }
    }


    fun getBoardList(){
        viewModelScope.launch {
            boardRepository.getBoardList().enqueue(object: Callback<BoardListResult>{
                override fun onResponse(call: Call<BoardListResult>, response: Response<BoardListResult>) {
                    _boardList.value = response.body()?.boardList
                    Log.d(TAG, "getBoardList: 게시글 가져오기 성공")
                }
                override fun onFailure(call: Call<BoardListResult>, t: Throwable) {
                    Log.d(TAG, "getBoardList: 게시글 가져오기 실패=$t")
                }
            })
        }
    }


    fun getUser(userId: String, userPwd: String){
        viewModelScope.launch {
            userRepository.getUser(userId, userPwd).run {
                let {
                    Log.d(TAG, "getUser: 로그인 성공")
                    _userData.postValue(it)
                }
            }
        }
    }

    fun insertUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.insertUser(user)
        }
    }

    fun deleteUserTable(){
        viewModelScope.launch {
            userRepository.deleteUserTable()
        }
    }

    fun insertBoard(board: Board){
        viewModelScope.launch {
            boardRepository.insertBoard(board)
        }
    }

    fun deleteBoardTable(){
        viewModelScope.launch {
            boardRepository.delete()
        }
    }




}