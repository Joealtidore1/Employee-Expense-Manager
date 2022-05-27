package com.impactech.expenser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.impactech.expenser.R
import com.impactech.expenser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        val anim = AnimationUtils.loadAnimation(this, R.anim.bounce_anim)
        binding.title.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, Authentication::class.java))
            finish()
        }, 1000)
    }
}