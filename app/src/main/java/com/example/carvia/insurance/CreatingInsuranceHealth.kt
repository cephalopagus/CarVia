package com.example.carvia.insurance

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.example.carvia.insurance.db.Health
import com.example.carvia.list_edit.SelectedDocProperty
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate

class CreatingInsuranceHealth : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_insurance_health)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        val slider = findViewById<SeekBar>(R.id.sliderbar_health)
        val kasko_proc = findViewById<TextView>(R.id.sum_health_price)
        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val str = (25000+progress*3500).toString()
                kasko_proc.setText(str)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        database.reference.child("users").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                val set_name = findViewById<TextView>(R.id.name_health)
                val name = it.child("name").value.toString()
                val set_phone = findViewById<TextView>(R.id.phone_health)
                val phone = it.child("phone").value.toString()
                val set_email = findViewById<TextView>(R.id.email_health)
                val email = it.child("email").value.toString()
                set_name.setText(name)
                set_phone.setText(phone)
                set_email.setText(email)

            }
        }

        val name_to_db: TextInputEditText = findViewById(R.id.name_health)
        val date_of_birth_to_db: TextInputEditText = findViewById(R.id.birth_health)
        val phone_to_db: TextInputEditText = findViewById(R.id.phone_health)
        val email_to_db: TextInputEditText = findViewById(R.id.email_health)
        val pasport_to_db: TextInputEditText = findViewById(R.id.pasport_health)
        val pasport_date_to_db: TextInputEditText = findViewById(R.id.pasport_date_health)
        val pasport_house_to_db: TextInputEditText = findViewById(R.id.pasport_house_health)
        val pasport_id_to_db: TextInputEditText = findViewById(R.id.pasport_id_health)
        val address_to_db: TextInputEditText = findViewById(R.id.address_health)
        val price = findViewById<TextView>(R.id.price_health)

        val btn_cal : MaterialButton = findViewById(R.id.btn_calculate_health)
        val btn_com : MaterialButton = findViewById(R.id.btn_create_health)


        btn_cal.setOnClickListener {
            val kasko_proc = findViewById<TextView>(R.id.sum_health_price)
            val price = findViewById<TextView>(R.id.price_health)
            kasko_proc.text.toString().toInt()

            price.setText((kasko_proc.text.toString().toFloat()*0.022f).toString())

        }

        btn_com.setOnClickListener {
            val db_id = database.reference.push().key!!
            val database= database.reference.child("health").child(db_id)

            val name = name_to_db.text.toString()
            val birth = date_of_birth_to_db.text.toString()
            val phone = phone_to_db.text.toString()
            val email = email_to_db.text.toString()
            val passport = pasport_to_db.text.toString()
            val passport_date = pasport_date_to_db.text.toString()
            val passport_house =  pasport_house_to_db.text.toString()
            val passport_id = pasport_id_to_db.text.toString()
            val address = address_to_db.text.toString()
            val price_total  = (price.text as String).toFloatOrNull()


            val currentDate = LocalDate.now()
            val end_date = currentDate.plusDays(365)

            val  health = Health(auth.currentUser!!.uid, name, birth, phone, email, passport, passport_date,
                passport_house, passport_id, address, currentDate.toString(), end_date.toString(), price_total, db_id)

            database.setValue(health).addOnSuccessListener {

                val intent = Intent(this, Payment::class.java)
                intent.putExtra("Price", price_total.toString())
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"Что то пошло не так ,попробуйте снова", Toast.LENGTH_LONG).show()
            }
            }
        }





}