package com.example.petsafe2.view

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.petsafe2.R
import com.example.petsafe2.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {
    
    companion object{
        const val TAG = "PetSafe2:: MainActivity"
    }

    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //상단바 숨김
        supportActionBar?.hide()

        //하단 navigationBar 설정
        val navController = findNavController(R.id.nav_host)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.calendar, R.id.exercise, R.id.home, R.id.board, R.id.mypage))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)

    }

    /**
     * 하단바 숨김 여부 설정
     * @param state true: GONE | false: VISIBLE
     */
    fun hideBottomNavi(state: Boolean){
        if(state){
            binding.bottomNav.visibility = View.GONE
        }else{
            binding.bottomNav.visibility = View.VISIBLE
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){

        }
    }



}