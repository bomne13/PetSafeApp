package com.example.petsafe2.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsafe2.view.MainViewModel
import com.example.petsafe2.R
import com.example.petsafe2.adapter.BoardAdapter
import com.example.petsafe2.databinding.FragmentBoardBinding
import com.example.petsafe2.view.MainActivity


class BoardFragment : Fragment(), View.OnClickListener{

    companion object{
        const val TAG = "PetSafe2:: BoardFragment"
    }

    lateinit var navController: NavController

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val adapter: BoardAdapter by lazy { BoardAdapter(mainViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //하단바 보이게 설정
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)

        //recyclerview adapter 연결
        binding.boardList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.boardList.adapter = adapter

        //리스트를 관찰하며 변경시 어댑터에 전달해줌(local db)
        mainViewModel.getBoards.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        //button clickEvent
        binding.searchBtn.setOnClickListener(this)
        binding.bellBtn.setOnClickListener(this)
        binding.boardAddBtn.setOnClickListener(this)


        /*
            mainViewModel.getBoardList()
            mainViewModel.boardList.observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
        */
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.searchBtn -> {}
            R.id.bellBtn -> {}
            R.id.boardAddBtn -> { navController.navigate(R.id.action_board_to_write) }
        }
    }

}