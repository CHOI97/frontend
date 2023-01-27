package com.example.simya.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simya.R
import com.example.simya.databinding.ActivityHomeBinding
import com.example.simya.homeFragment.LocationFragment
import com.example.simya.homeFragment.MainFragment
import com.example.simya.mypageFragment.MyPageProfileFragment
import com.example.simya.mystoryFragment.MyStoryFragment

class HomeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

//        fragment 전환
        binding.bnvHomeNavi.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.tab_home_main_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.fmHome.id, MainFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.tab_home_main_my_chat -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.fmHome.id, MyStoryFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.tab_home_main_location -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.fmHome.id, LocationFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.tab_home_main_my -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.fmHome.id, MyPageProfileFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.tab_home_main_home
        }
    }
}
