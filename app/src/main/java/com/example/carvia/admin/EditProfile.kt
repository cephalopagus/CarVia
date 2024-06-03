package com.example.carvia.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.R
import com.example.carvia.auth.Users
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EditProfile : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val set_name = findViewById<TextView>(R.id.editable_name)
        val set_phone = findViewById<TextView>(R.id.editable_phone)
        val set_email = findViewById<TextView>(R.id.editable_email)
        val btn_edit = findViewById<MaterialButton>(R.id.complete_btn_edit)


        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val uid = intent.getStringExtra("UID").toString()


        database.reference.child("users").child(uid).get().addOnSuccessListener {
            if (it.exists()){

                val name = it.child("name").value.toString()
                val phone = it.child("phone").value.toString()
                val email = it.child("email").value.toString()
                set_name.setText(name)
                set_phone.setText(phone)
                set_email.setText(email)

            }
        }
        btn_edit.setOnClickListener {
            val name = set_name.text
            val phone = set_phone.text
            val email = set_email.text
            val uid = uid.toString()



            val users: Users = Users(name.toString(),email.toString(), phone.toString(),uid)
            database.reference.child("users").child(uid).setValue(users)
                .addOnSuccessListener {
                startActivity(Intent(this, AdminPanel::class.java))
                Toast.makeText(this, "Данные обновлнены!",
                    Toast.LENGTH_LONG).show()
            }.addOnCanceledListener {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
            }}






    }
}