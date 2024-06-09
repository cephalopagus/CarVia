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
import com.example.carvia.insurance.db.Health
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SelectedDocHealth : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_doc_health)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val id = intent.getStringExtra("Id").toString()
        val btn: MaterialButton = findViewById(R.id.complete_btn_doc_edit_health)


        val set_name = findViewById<TextView>(R.id.editable_name_health)
        val set_birth = findViewById<TextView>(R.id.editable_birth_health)
        val set_pasport = findViewById<TextView>(R.id.editable_pasport_health)
        val set_pasport_date = findViewById<TextView>(R.id.editable_pasport_date_health)
        val set_pasport_house = findViewById<TextView>(R.id.editable_pasport_house_health)
        val set_pasport_id = findViewById<TextView>(R.id.editable_pasport_id_health)
        val set_address = findViewById<TextView>(R.id.editable_address_health)
        val set_price = findViewById<TextView>(R.id.editable_price_health)
        var uid = " "
        var phone = " "
        var date_order = " "
        var date_order_end = " "
        var email = " "
        val btn_dlt:MaterialButton = findViewById(R.id.delete_health)
        btn_dlt.setOnClickListener {
            database.reference.child("health").child(id).removeValue().
            addOnSuccessListener {
                startActivity(Intent(this, AdminPanel::class.java))
                Toast.makeText(this, "Полис удален!", Toast.LENGTH_LONG).show()
            }.addOnCanceledListener {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
            }
        }

        database.reference.child("health").child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value.toString()
                val birth = it.child("birth").value.toString()
                val pasport = it.child("pasport").value.toString()
                val pasport_date = it.child("pasport_date").value.toString()
                val pasport_house = it.child("pasport_house").value.toString()
                val pasport_id = it.child("pasport_id").value.toString()
                val address = it.child("address").value.toString()
                val price = it.child("price").value.toString()

                set_name.setText(name)
                set_birth.setText(birth)
                set_pasport.setText(pasport)
                set_pasport_date.setText(pasport_date)
                set_pasport_house.setText(pasport_house)
                set_pasport_id.setText(pasport_id)
                set_address.setText(address)
                set_price.setText(price)
                uid = it.child("uid").value.toString()
                phone = it.child("phone").value.toString()
                date_order = it.child("date_order").value.toString()
                date_order_end = it.child("date_order_end").value.toString()
                email = it.child("email").value.toString()
            }
        }

        btn.setOnClickListener {
            val health: Health = Health(uid, set_name.text.toString(), set_birth.text.toString(),  phone, email, set_pasport.text.toString(),
                set_pasport_date.text.toString(), set_pasport_house.text.toString(), set_pasport_id.text.toString(),
                set_address.text.toString(), date_order, date_order_end, set_price.text.toString().toFloat(), id)

            database.reference.child("health").child(id).setValue(health).addOnSuccessListener {
                startActivity(Intent(this, AdminPanel::class.java))
                Toast.makeText(this, "Данные обновлнены!",
                    Toast.LENGTH_LONG).show()
            }.addOnCanceledListener {
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
            }
        }
    }
}