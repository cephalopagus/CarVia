package com.example.carvia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.auth.Authorization
import com.example.carvia.auth.Registration

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val buttonRgs = findViewById<Button>(R.id.registation_btn)
        buttonRgs.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }
        val buttonAtr = findViewById<Button>(R.id.authorization_btn)
        buttonAtr.setOnClickListener {
            startActivity(Intent(this, Authorization::class.java))
        }

    }
}