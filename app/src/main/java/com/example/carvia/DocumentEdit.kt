package com.example.carvia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.insurance.db.Osago
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class DocumentEdit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_edit)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        val osago_btn:CardView = findViewById(R.id.recycler_osago_edit)
        val kasko_btn:CardView = findViewById(R.id.recycler_kasko_edit)
        val health_btn:CardView = findViewById(R.id.recycler_health_edit)
        val property_btn:CardView = findViewById(R.id.recycler_property_edit)

        val uid = intent.getStringExtra("UID").toString()


        osago_btn.setOnClickListener {
            startActivity(Intent(this, DocumentListEdit::class.java).apply {
                putExtra("UID", uid)
            })


        }
        kasko_btn.setOnClickListener {  }
        health_btn.setOnClickListener {  }
        property_btn.setOnClickListener {  }



    }
}