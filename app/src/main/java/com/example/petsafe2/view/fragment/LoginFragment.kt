package com.example.petsafe2.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.petsafe2.R
import com.example.petsafe2.databinding.FragmentLoginBinding
import com.example.petsafe2.model.User
import com.example.petsafe2.view.MainActivity
import com.example.petsafe2.view.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment(), View.OnClickListener {

    companion object {
        const val TAG = "PetSafe2:: LoginFragment"
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController

    private val mainViewModel: MainViewModel by viewModels()

    private var googleSignInClient: GoogleSignInClient? = null
    private var GOOGLE_LOGIN_CODE = 9001

    private val args by navArgs<LoginFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        //googleSignInClient build
        googleSignInClient = mainViewModel.getGoogleSignInClient(binding.root.context, getString(R.string.default_web_client_id))

        //?????????????????? ?????????????????? ????????? ?????? ??????
        if (args.logoutCheck) { logout() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //????????? ????????? ??????
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)

        val user = User("testId", "pwd", "?????????", "testId@naver.com", "??????2", "2023-01-17")
        mainViewModel.insertUser(user)

        mainViewModel.loginCheck.observe(viewLifecycleOwner) {
            when (it) {
                "success" -> successLogin()
                "fail" -> Toast.makeText(activity, "???????????? ??????????????? ??????????????????", Toast.LENGTH_SHORT).show()
            }
        }

        //button clickEvent
        binding.backBtn.setOnClickListener(this)
        binding.googleBtn.setOnClickListener(this)
        binding.loginBtn.setOnClickListener {
            val userId = binding.EditId.text.toString()
            val userPwd = binding.EditPwd.text.toString()

            if (userId.isEmpty() || userPwd.isEmpty()) {
                Toast.makeText(activity, "???????????? ??????????????? ??????????????????", Toast.LENGTH_SHORT).show()
            } else {
                mainViewModel.getLoginCheck(userId, userPwd)
            }
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backBtn -> { navController.popBackStack() }
            R.id.googleBtn -> {
                val signInIntent = googleSignInClient?.signInIntent
                startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GOOGLE_LOGIN_CODE -> {
                try {
                    //Intent Result?????? google login ?????? ?????????
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                    //google login ????????? ??????
                    if (task.isSuccessful) {
                        Log.d(TAG, "onActivityResult: google Login Success")
                        successLogin()

                        //???????????? ????????? ?????? ??????
                        val account = task.getResult(ApiException::class.java)!!
                        mainViewModel.googleSignAuth(account)
                    }
                } catch (e: ApiException) {
                    Log.d(TAG, "onActivityResult: google Login Fail = $e")
                    Toast.makeText(activity, "${e.statusCode} : ???????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /** ?????? ???????????? ?????? */
    private fun successLogin() {
        navController.navigate(R.id.action_login_to_home)
    }

    /** ?????? ????????? ?????? ??????(???????????? ??? ?????? ????????? ??????) */
    private fun logout() {
        googleSignInClient!!.signOut()
    }



}