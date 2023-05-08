package com.falikiali.githubusersapp.presentation.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.falikiali.githubusersapp.R
import com.falikiali.githubusersapp.databinding.ActivitySplashBinding
import com.falikiali.githubusersapp.presentation.activity.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startIntent()
    }

    private fun startIntent() {
        Handler(mainLooper).postDelayed({
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i).also { finish() }
            overridePendingTransition(0, R.anim.fade_out)
        }, 3500)
    }
}