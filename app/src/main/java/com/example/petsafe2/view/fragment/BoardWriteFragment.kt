package com.example.petsafe2.view.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.petsafe2.view.MainViewModel
import com.example.petsafe2.R
import com.example.petsafe2.model.Board
import com.example.petsafe2.databinding.FragmentBoardWriteBinding
import com.example.petsafe2.view.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate
import java.util.*

class BoardWriteFragment : Fragment(), View.OnClickListener {

    companion object{
        const val TAG = "PetSafe2:: BoardWriteFragment"
    }

    lateinit var navController: NavController

    private var _binding: FragmentBoardWriteBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    val IMAGE_PICK = 1111
    var selectImage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //하단바 숨기는 기능
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)

        //button clickEvent
        binding.backBtn.setOnClickListener(this)
        binding.photoAddBtn.setOnClickListener(this)
        binding.submitBtn.setOnClickListener{
            val title = binding.writeTitle.text.toString()
            val content = binding.writeContent.text.toString()
            submitBoard(title, content)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backBtn ->  { navController.popBackStack() }
            R.id.photoAddBtn -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, IMAGE_PICK)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            IMAGE_PICK -> {
                if(resultCode == Activity.RESULT_OK){
                    selectImage = data?.data
                    binding.testIv.setImageURI(selectImage)
                }else{
                    Toast.makeText(activity, "이미지를 불러오지 못했습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /** 게시글 내용 전송
     * @param title 입력된 게시글 제목
     * @param content 입력된 게시글 내용
     * */
    private fun submitBoard(title: String, content: String){
        val todayDate: LocalDate = LocalDate.now()

        if(title.isEmpty() || content.isEmpty()){
            Toast.makeText(activity, "제목과 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
        }else{
            val board = Board(title = title, content = content, userId = "testId", regDate = "$todayDate")
            mainViewModel.insertBoard(board)
            val navAction = BoardWriteFragmentDirections.actionWriteToDetail(board = arrayOf(board))
            navController.navigate(navAction)
        }
    }

}