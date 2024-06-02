package com.example.carvia.insurance

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.example.carvia.insurance.db.Health
import com.example.carvia.insurance.db.Property
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate

class CreatingInsuranceProperty : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_insurance_property)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        val slider_one = findViewById<SeekBar>(R.id.sliderbar_one_property)
        val sum_one = findViewById<TextView>(R.id.sum_one_property)
        slider_one.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val str = (200000+progress*18000).toString()
                sum_one.setText(str)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val slider_two = findViewById<SeekBar>(R.id.sliderbar_two_property)
        val sum_two = findViewById<TextView>(R.id.sum_two_property)
        slider_two.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val str = (100000+progress*9000).toString()
                sum_two.setText(str)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val slider_three = findViewById<SeekBar>(R.id.sliderbar_three_property)
        val sum_three = findViewById<TextView>(R.id.sum_three_property)
        slider_three.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val str = (100000+progress*9000).toString()
                sum_three.setText(str)
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
                val set_name = findViewById<TextView>(R.id.name_property)
                val name = it.child("name").value.toString()
                val set_phone = findViewById<TextView>(R.id.phone_property)
                val phone = it.child("phone").value.toString()
                val set_email = findViewById<TextView>(R.id.email_property)
                val email = it.child("email").value.toString()
                set_name.setText(name)
                set_phone.setText(phone)
                set_email.setText(email)

            }
        }

        val name_to_db: TextInputEditText = findViewById(R.id.name_property)
        val date_of_birth_to_db: TextInputEditText = findViewById(R.id.birth_property)
        val phone_to_db: TextInputEditText = findViewById(R.id.phone_property)
        val email_to_db: TextInputEditText = findViewById(R.id.email_property)
        val pasport_to_db: TextInputEditText = findViewById(R.id.pasport_property)
        val pasport_date_to_db: TextInputEditText = findViewById(R.id.pasport_date_property)
        val pasport_house_to_db: TextInputEditText = findViewById(R.id.pasport_house_property)
        val pasport_id_to_db: TextInputEditText = findViewById(R.id.pasport_id_property)
        val address_to_db: TextInputEditText = findViewById(R.id.address_property)
        val price = findViewById<TextView>(R.id.price_property)
        val finishing = findViewById<TextView>(R.id.sum_one_property)
        val decoration = findViewById<TextView>(R.id.sum_two_property)
        val responsibility = findViewById<TextView>(R.id.sum_three_property)

        val btn_cal : MaterialButton = findViewById(R.id.btn_calculate_property)
        val btn_com : MaterialButton = findViewById(R.id.btn_create_property)


        btn_cal.setOnClickListener {
            val price = findViewById<TextView>(R.id.price_property)

            val proc_1 = finishing.text.toString().toFloat()*0.003
            val proc_2 = decoration.text.toString().toFloat()*0.004
            val proc_3 = responsibility.text.toString().toFloat()*0.004
            price.setText((1600+proc_1+proc_2+proc_3).toString())
        }

        btn_com.setOnClickListener {
            val db_id = database.reference.push().key!!
            val database= database.reference.child("property").child(db_id)

            val name = name_to_db.text.toString()
            val birth = date_of_birth_to_db.text.toString()
            val phone = phone_to_db.text.toString()
            val email = email_to_db.text.toString()
            val passport = pasport_to_db.text.toString()
            val passport_date = pasport_date_to_db.text.toString()
            val passport_house =  pasport_house_to_db.text.toString()
            val passport_id = pasport_id_to_db.text.toString()
            val address = address_to_db.text.toString()
            val finishing = finishing.text.toString()
            val decoration = decoration.text.toString()
            val responsibility = responsibility.text.toString()
            val price_total  = (price.text as String).toFloatOrNull()
            val currentDate = LocalDate.now()
            val end_date = currentDate.plusDays(365)


            val  property = Property(auth.currentUser!!.uid, name, birth, phone, email, passport, passport_date,
                passport_house, passport_id, address, finishing, decoration, responsibility,
                currentDate.toString(), end_date.toString(), price_total, db_id)

            database.setValue(property).addOnSuccessListener {
                Toast.makeText(this,"Документ оформлен!", Toast.LENGTH_LONG).show()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"Что то пошло не так ,попробуйте снова", Toast.LENGTH_LONG).show()
            }
        }


    }
}