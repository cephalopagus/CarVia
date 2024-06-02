package com.example.carvia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import com.example.carvia.list_edit.DocumentListEditHealth
import com.example.carvia.list_edit.DocumentListEditKasko
import com.example.carvia.list_edit.DocumentListEditOsago
import com.example.carvia.list_edit.DocumentListEditProperty

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
            startActivity(Intent(this, DocumentListEditOsago::class.java).apply {
                putExtra("UID", uid)
            })


        }
        kasko_btn.setOnClickListener {
            startActivity(Intent(this, DocumentListEditKasko::class.java).apply {
                putExtra("UID", uid)
            })
        }
        health_btn.setOnClickListener {
            startActivity(Intent(this, DocumentListEditHealth::class.java).apply {
                putExtra("UID", uid)
            })
        }
        property_btn.setOnClickListener {
            startActivity(Intent(this, DocumentListEditProperty::class.java).apply {
                putExtra("UID", uid)
            })
        }



    }
}