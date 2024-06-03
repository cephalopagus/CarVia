package com.example.carvia.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.example.carvia.WelcomeScreen
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Authorization : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()



        val auth_login: TextInputEditText = findViewById(R.id.login_admin)
        val auth_password: TextInputEditText = findViewById(R.id.password_admin)
        val auth_btn: MaterialButton = findViewById(R.id.complete_btn_auth_admin)
        val btn_back: MaterialButton = findViewById(R.id.btn_back)


        btn_back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        auth_btn.setOnClickListener {
            val email = auth_login.text.toString()
            val password = auth_password.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Не оставляйте поля пустыми", Toast.LENGTH_LONG).show()
            }else if (email.equals("admin") && password.equals("admin")){
                startActivity(Intent(this, AdminPanel::class.java))
            }
            else{
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Отказано в доступе!", Toast.LENGTH_LONG).show()
            }
        }



    }
}