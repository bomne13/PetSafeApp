package com.example.petsafe2.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.petsafe2.databinding.FragmentHomeBinding
import com.example.petsafe2.view.MainActivity

class HomeFragment: Fragment() {

    companion object{
        const val TAG = "PetSafe2:: HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView: 실행됨")
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        //하단바 보이게 설정
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)
    }
}