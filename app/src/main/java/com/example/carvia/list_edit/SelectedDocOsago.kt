package com.example.carvia.list_edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.AdminPanel
import com.example.carvia.R
import com.example.carvia.insurance.db.Osago
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SelectedDocOsago : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_doc)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        val name = intent.getStringExtra("Name").toString()
        val uid = intent.getStringExtra("UID").toString()
        val phone = intent.getStringExtra("Phone").toString()
        val type = intent.getStringExtra("Type").toString()
        val diag = intent.getStringExtra("Diagnostic").toString()
        val foreign = intent.getStringExtra("Foreign").toString()
        val exp = intent.getStringExtra("Exp").toString()
        val period = intent.getStringExtra("Per").toString()
        val date = intent.getStringExtra("Date").toString()
        val date_end = intent.getStringExtra("Date_end").toString()
        val price = intent.getStringExtra("Price").toString()
        val id = intent.getStringExtra("Id").toString()

        val set_name = findViewById<TextView>(R.id.editable_name_osago)
        val set_type = findViewById<TextView>(R.id.editable_type_osago)
        val set_exp = findViewById<TextView>(R.id.editable_exp_osago)
        val set_per = findViewById<TextView>(R.id.editable_period_osago)
        val set_price = findViewById<TextView>(R.id.editable_price_osago)
        val btn:MaterialButton = findViewById(R.id.complete_btn_doc_edit)


        set_name.setText(name)
        set_type.setText(type)
        set_exp.setText(exp)
        set_per.setText(period)
        set_price.setText(price)


        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()


        btn.setOnClickListener {

            val names = set_name.text.toString()
            val types = set_type.text.toString()
            val exps = set_exp.text.toString()
            val pers = set_per.text.toString()
            val prices = set_price.text.toString()


            val osago:Osago = Osago(uid, names, phone, types, diag, foreign, exps, pers, date, date_end, prices.toFloat(), id)
            database.reference.child("osago").child(id).setValue(osago).addOnSuccessListener {
                startActivity(Intent(this, AdminPanel::class.java))
                Toast.makeText(this, "Данные обновлнены!",
                    Toast.LENGTH_LONG).show()
            }.addOnCanceledListener {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
            }
        }

    }
}