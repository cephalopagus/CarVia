package com.example.carvia.list_edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.admin.AdminPanel
import com.example.carvia.R
import com.example.carvia.insurance.db.Kasko
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SelectedDocKasko : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_doc_kasko)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val btn: MaterialButton = findViewById(R.id.complete_btn_doc_edit_kasko)
        val id = intent.getStringExtra("Id").toString()

        val set_name = findViewById<TextView>(R.id.editable_name_kasko)
        val set_model = findViewById<TextView>(R.id.editable_model_kasko)
        val set_year = findViewById<TextView>(R.id.editable_year_car_kasko)
        val set_price_auto = findViewById<TextView>(R.id.editable_price_car_kasko)
        val set_procent = findViewById<TextView>(R.id.editable_procent_kasko)
        val set_price = findViewById<TextView>(R.id.editable_price_kasko)
        var uid = " "
        var phone = " "
        var date_order = " "
        var date_order_end = " "


        database.reference.child("kasko").child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value.toString()
                val model = it.child("model_auto").value.toString()
                val year = it.child("year_auto").value.toString()
                val price_auto = it.child("price_auto").value.toString()
                val procent = it.child("procent").value.toString()
                val price = it.child("price").value.toString()



                uid = it.child("uid").value.toString()
                phone = it.child("phone").value.toString()
                date_order = it.child("date_order").value.toString()
                date_order_end = it.child("date_order_end").value.toString()

                set_name.setText(name)
                set_model.setText(model)
                set_year.setText(year)
                set_price_auto.setText(price_auto)
                set_procent.setText(procent)
                set_price.setText(price)
            }
        }

        btn.setOnClickListener {
            val kasko:Kasko = Kasko(uid, set_name.text.toString(),  phone, set_model.text.toString(), set_year.text.toString().toInt(),
                set_price_auto.text.toString().toInt(), set_procent.text.toString().toInt(),
                date_order, date_order_end, set_price.text.toString().toFloat(), id)
            database.reference.child("kasko").child(id).setValue(kasko).addOnSuccessListener {
                startActivity(Intent(this, AdminPanel::class.java))
                Toast.makeText(this, "Данные обновлнены!",
                    Toast.LENGTH_LONG).show()
            }.addOnCanceledListener {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
            }
        }
    }
}