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
import androidx.viewpager2.widget.ViewPager2
import com.example.petsafe2.R
import com.example.petsafe2.adapter.ImagePagerAdapter
import com.example.petsafe2.databinding.FragmentGuideBinding
import com.example.petsafe2.view.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount


class GuideFragment : Fragment(), View.OnClickListener {

    companion object{
        const val TAG = "PetSafe2:: GuideFragment"
    }

    lateinit var navController: NavController

    private var _binding: FragmentGuideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //이전 로그인 여부 확인
        val account = GoogleSignIn.getLastSignedInAccount(binding.root.context)
        loginCheck(account)

        //guideImage adapter와 viewPager2 연결
        binding.guideImage.adapter = ImagePagerAdapter(getGuideImage())
        binding.guideImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //하단 dots 설정
        binding.dotsIndicator.attachTo(binding.guideImage)

        //button clickEvent
        binding.loginBtn.setOnClickListener(this)
        binding.joinBtn.setOnClickListener(this)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        //하단바 숨김
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.loginBtn -> {
                val navAction = GuideFragmentDirections.actionGuideToLogin(false)
                navController.navigate(navAction)
            }
            R.id.joinBtn -> {  }
        }
    }

    /**
     * 로그인 기록이 있으면 메인 화면으로 이동
     * @param account googleLogin 기록이 있으면 계정 정보 저장되어 있음
     */
    private fun loginCheck(account: GoogleSignInAccount?) {
        account?.let { navController.navigate(R.id.action_guide_to_home) }
    }

    private fun getGuideImage(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.img_description_warning,
            R.drawable.img_description_exercise,
            R.drawable.img_description_community,
            R.drawable.img_description_alarm
        )
    }




}