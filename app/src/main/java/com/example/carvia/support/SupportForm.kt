package com.example.carvia.support

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate

class SupportForm : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_form)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val btn:MaterialButton= findViewById(R.id.btn_create_msg)
        val msg:EditText= findViewById(R.id.msg_text)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        btn.setOnClickListener {
            val msg_text = msg.text.toString()
            val currentDate = LocalDate.now()

            val message:Message = Message(msg_text, currentDate.toString())

            database.reference.child("support_msg").child(auth.currentUser!!.uid).setValue(
                message
            ).addOnSuccessListener {
                Toast.makeText(this,"Сообщение отправлено!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }.addOnCanceledListener {
                Toast.makeText(this,"Что-то не так!", Toast.LENGTH_LONG).show()
            }
        }
    }
}