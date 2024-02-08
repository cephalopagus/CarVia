package com.example.carvia.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Authorization : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private  val emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        auth= FirebaseAuth.getInstance()
        val auth_email:TextInputEditText = findViewById(R.id.email_input_auth)
        val auth_password:TextInputEditText = findViewById(R.id.password_input_auth)
        val auth_btn:MaterialButton = findViewById(R.id.complete_btn_auth)

        auth_btn.setOnClickListener {
            val email = auth_email.text.toString()
            val password = auth_password.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                if (email.isEmpty()){
                    auth_email.error = "Введите свою почту"
                }
                if (password.isEmpty()){
                    auth_password.error = "Введите пароль"
                }
                Toast.makeText(this, "Не оставляйте поля пустыми", Toast.LENGTH_LONG).show()
            }
            else if (!email.matches(emailPattern.toRegex())){
                auth_email.error="Не корестный email адрес"
            }
            else if (password.length < 6){
                auth_password.error = "Слишком короткий пароль"
            }else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Не верные данные", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}