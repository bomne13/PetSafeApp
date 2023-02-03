package com.example.petsafe2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.petsafe2.R
import com.example.petsafe2.databinding.FragmentBoardDetailBinding
import com.example.petsafe2.view.MainActivity

class BoardDetailFragment : Fragment(), View.OnClickListener {

    companion object{
        const val TAG = "PetSafe2:: BoardDetailFragment"
    }

    lateinit var navController: NavController

    private var _binding: FragmentBoardDetailBinding? = null
    private val binding get() = _binding!!

    //게시글 내용 저장할 변수
    private val args by navArgs<BoardDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //하단바 숨기는 기능
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)

        //게시글 내용 binding
        binding.run {
            val board = args.let { args.board[0] }

            title.text = board.title
            writer.text = board.userId
            writeDate.text = board.regDate
            content.text = board.content
            heartBtn.text = board.likeCnt.toString()
            replyBtn.text = board.commentCnt.toString()
        }

        //button clickEvent
        binding.backBtn.setOnClickListener(this)
        binding.moreMenuBtn.setOnClickListener(this)
        binding.replySubmitBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.backBtn -> { navController.popBackStack() }
            R.id.moreMenuBtn -> {  }
            R.id.replySubmitBtn -> {  }
        }
    }


}