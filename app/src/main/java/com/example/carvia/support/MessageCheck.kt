package com.example.carvia.support

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.R
import com.example.carvia.admin.AdminPanel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageCheck : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_check)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val btn:MaterialButton = findViewById(R.id.delete_msg_btn)
        val uid = intent.getStringExtra("UID")
        val msg_txt:TextView = findViewById(R.id.text_msg)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        if (uid != null) {
            database.reference.child("support_msg").child(uid).get().addOnSuccessListener {
                if (it.exists()){
                    val msg = it.child("msg"). value.toString()
                    msg_txt.text = msg
                }else {
                    btn.visibility = View.GONE
                    msg_txt.text = "Пользователь не оставлял сообщений)"
                }
            }
        }

        btn.setOnClickListener {
            database.reference.child("support_msg").child(uid!!).removeValue().
                    addOnSuccessListener {
                        startActivity(Intent(this, AdminPanel::class.java))
                        Toast.makeText(this, "Сообщения удалено!", Toast.LENGTH_LONG).show()
                    }.addOnCanceledListener {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
                    }

        }

    }
}