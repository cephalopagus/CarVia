package com.example.carvia.admin

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.R
import com.example.carvia.support.MessageCheck
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase

class SelectedUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_user)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val name = intent.getStringExtra("Name").toString()
        val uid = intent.getStringExtra("UID").toString()
        val str:TextView = findViewById(R.id.name_redact)
        str.text = name

        val edit_profile_btn : MaterialButton  = findViewById(R.id.edit_profile_btn)
        val edit_doc_btn : MaterialButton  = findViewById(R.id.edit_document_btn)
        val delete_profile_btn : MaterialButton  = findViewById(R.id.delete_profile_btn)
        val check_msg_btn : MaterialButton  = findViewById(R.id.check_msg_btn)

        check_msg_btn.setOnClickListener {
            startActivity(Intent(this, MessageCheck::class.java).apply {
                putExtra("UID", uid)
            })
        }

        edit_profile_btn.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java).apply {
                putExtra("UID", uid)
            })

        }
        edit_doc_btn.setOnClickListener {
            startActivity(Intent(this, DocumentEdit::class.java).apply {
                putExtra("UID", uid)
            })
        }
        delete_profile_btn.setOnClickListener {

            val message : String? = "Вы уверенны, что хотите удалить пользователя?"
            showcustomdialog(message, uid)

        }


    }

    private fun showcustomdialog(message: String? , uid: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_window)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val btn_yes:MaterialButton = dialog.findViewById(R.id.yes_btn)
        val btn_no:MaterialButton = dialog.findViewById(R.id.no_btn)

        btn_no.setOnClickListener {
            dialog.dismiss()
        }



        btn_yes.setOnClickListener {
            val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("users").child(uid.toString())
            firebaseDatabase.removeValue().addOnSuccessListener {
                startActivity(Intent(this, AdminPanel::class.java))
                Toast.makeText(this, "Пользователь удален!", Toast.LENGTH_LONG).show()
            }.addOnCanceledListener {
                    Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()}}

        dialog.show()

    }
}