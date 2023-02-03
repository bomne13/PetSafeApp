package com.example.petsafe2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.petsafe2.R
import com.example.petsafe2.databinding.FragmentMypageBinding
import com.example.petsafe2.view.MainActivity

class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //하단바 보이게 설정
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)

        binding.settingBtn.setOnClickListener {
            navController.navigate(R.id.action_mypage_to_setting)
        }
    }
}