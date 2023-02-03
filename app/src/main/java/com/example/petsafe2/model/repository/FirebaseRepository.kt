package com.example.petsafe2.model.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.petsafe2.model.UserAccess
import com.example.petsafe2.view.MainViewModel
import com.example.petsafe2.view.fragment.LoginFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.suspendCoroutine

class FirebaseRepository {

    companion object{
        const val TAG = "PetSafe2:: FirebaseRepository"
    }

    private var _loginCheck = MutableLiveData<String>()
    private val loginCheck: LiveData<String>
        get() = _loginCheck

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore:FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    /** google 로그인 옵션 설정
     * @param clientId google-services clientId
     * */
    private fun createGoogleSigInInOptions(clientId: String): GoogleSignInOptions
        = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()

    fun createGoogleSignInClient(context: Context, clientId: String): GoogleSignInClient =
        GoogleSignIn.getClient(context, createGoogleSigInInOptions(clientId))


    /** auth에서 사용자 정보 가져옴
     * @param acct 로그인한 User 정보
     * @return if(success){ FirebaseUser } eles{ errorMessage }
    */
    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount): Task<AuthResult>{
        Log.d(TAG, "firebaseAuthWithGoogle: 실행")
        //로그인한 사용자의 정보
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        return auth.signInWithCredential(credential).addOnCompleteListener(Activity()){ task->
            if(task.isSuccessful){
                //현재 로그인된 사용자 반환
                Log.d(FirebaseRepository.TAG, "firebaseAuthWithGoogle: 로그인 성공")
            }else{
                //error message 반환
                Log.w(FirebaseRepository.TAG, "firebaseAuthWithGoogle: fail", task.exception)
            }
        }
    }

    suspend fun userCheck(acc: FirebaseUser): Task<QuerySnapshot> {

        return withContext(Dispatchers.IO){
            firestore.collection("User").whereEqualTo("email", acc.email).get()
                .addOnSuccessListener { result ->
                    if(result.isEmpty){
                        launch { uploadUserFirestore(acc).await() }
                    }else{
                        launch { updateUserFirestore(acc).await() }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "userCheck: 데이터 가져오기 실패 (${exception}")
                }
        }


    }


    /** Firestore에 User 정보 저장
     * @param acc 로그인 성공한 User 정보
     * @return firestore 저장 성공 여부
    */
    suspend fun uploadUserFirestore(acc: FirebaseUser): Task<DocumentReference> {

        Log.d(LoginFragment.TAG, "uploadUserFirestore: 실행")
        val user = UserAccess(acc.uid, acc.email!!, null, acc.displayName!!, acc.displayName, acc.photoUrl)

        return  withContext(Dispatchers.IO){
                    firestore.collection("User").add(user)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                Log.d(TAG, "uploadUserFirestore: user 정보 업로드")
                            }else{
                                Log.d(TAG, "uploadUserFirestore: user 정보 업로드 실패")
                            }
                        }
                }
    }

    /** Firestore에 이미 등록된 email인 경우 idToken update
     * @param user 로그인 성공한 User 정보
     * @return firestore 업데이트 성공 여부
     * */
    private suspend fun updateUserFirestore(acc: FirebaseUser): Task<Void>{

        Log.d(TAG, "updateUserFirestore: 실행")
        val user = UserAccess(acc.uid, acc.email!!, null, acc.displayName!!, acc.displayName, acc.photoUrl)

        return withContext(Dispatchers.IO){
                    firestore.collection("User").document(user.toString()).update("idToken", user.idToken)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                Log.d(TAG, "updateUserFirestore: user 정보 업데이트 성공")
                            }else{
                                Log.d(TAG, "updateUserFirestore: user 정보 업데이트 실패")
                            }
                        }
                }
    }


}