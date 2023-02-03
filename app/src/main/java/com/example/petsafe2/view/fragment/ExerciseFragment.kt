package com.example.petsafe2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petsafe2.databinding.FragmentExerciseBinding
import com.example.petsafe2.view.MainActivity


class ExerciseFragment : Fragment() {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //하단바 보이게 설정
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)
    }
}