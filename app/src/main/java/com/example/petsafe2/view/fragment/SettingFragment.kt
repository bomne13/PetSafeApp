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
import com.example.petsafe2.R
import com.example.petsafe2.databinding.FragmentSettingBinding
import com.example.petsafe2.view.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SettingFragment : Fragment(), View.OnClickListener {

    companion object{
        private val TAG = "PetSafe2:: SettingFragment"
    }

    lateinit var navController: NavController

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //하단바 숨기는 기능
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)

        //button clickEvent
        binding.logoutBtn.setOnClickListener(this)
        binding.backBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backBtn -> { navController.popBackStack() }
            R.id.logoutBtn -> { signOut() }
        }
    }

    /** 로그아웃 */
    private fun signOut(){
        auth.signOut()

        //로그인 화면으로 이동
        val navAction = SettingFragmentDirections.actionSettingToLogin(true)
        navController.navigate(navAction)
    }



}