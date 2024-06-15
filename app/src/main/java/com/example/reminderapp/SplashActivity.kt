package com.example.reminderapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val secondsDelayed = 1
        Handler().postDelayed({
            startActivity(
                Intent(
                    applicationContext,
                    MainActivity::class.java
                )
            ) //after 500 milliseconds this block calls the mainActivity
            finish()
        }, (secondsDelayed * 500).toLong())
    }
}