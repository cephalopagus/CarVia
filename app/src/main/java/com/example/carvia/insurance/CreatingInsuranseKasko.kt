package com.example.carvia.insurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.example.carvia.insurance.db.Kasko
import com.example.carvia.insurance.db.Osago
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

class CreatingInsuranseKasko : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_insuranse_kasko)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        val slider = findViewById<SeekBar>(R.id.sliderbar)
        val kasko_proc = findViewById<TextView>(R.id.kasko_procent)
        slider.setOnSeekBarChangeListener(object :OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress in 0..24){
                    kasko_proc.text = "0"
                }else if (progress in 25..50){
                    kasko_proc.text = "1"
                }
                else if (progress in 51..75){
                    kasko_proc.text = "2"
                }
                else{
                    kasko_proc.text = "3"
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        val name_to_db: TextInputEditText = findViewById(R.id.name_kasko)
        val phone_to_db: TextInputEditText = findViewById(R.id.phone_kasko)

        val model_auto_to_db: TextInputEditText = findViewById(R.id.model_auto_kasko)
        val year_auto_to_db: TextInputEditText = findViewById(R.id.year_auto_kasko)
        val price_auto_to_db: TextInputEditText = findViewById(R.id.price_auto_kasko)
        val procent_kasko:TextView = findViewById(R.id.kasko_procent)


        val btn_kasko: MaterialButton = findViewById(R.id.btn_create_kasko)
        val btn_cal_kasko: MaterialButton = findViewById(R.id.btn_calculate_kasko)
        var price_kasko:TextView = findViewById(R.id.price_kasko)


        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        database.reference.child("users").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                val set_name = findViewById<TextView>(R.id.name_kasko)
                val name = it.child("name").value.toString()
                val set_phone = findViewById<TextView>(R.id.phone_kasko)
                val phone = it.child("phone").value.toString()
                set_name.setText(name)
                set_phone.setText(phone)

            }
        }


        price_kasko.text = "0.0"
        var price=0f

        btn_cal_kasko.setOnClickListener {
            price_kasko.setText("")

            var car_p = price_auto_to_db.text.toString().toFloat()
            car_p *= 0.04f
            if(procent_kasko.text.toString().toInt()==1){
                car_p=car_p-car_p*0.125f*1
            }else if(procent_kasko.text.toString().toInt()==2){
                car_p=car_p-car_p*0.125f*2
            }else if(procent_kasko.text.toString().toInt()==3){
                car_p=car_p-car_p*0.125f*3
            }else if(procent_kasko.text.toString().toInt()==0){
                car_p=car_p
            }

            price_kasko.setText(car_p.toString())

        }
        btn_kasko.setOnClickListener {
            val name = name_to_db.text.toString()
            val phone = phone_to_db.text.toString()
            val model = model_auto_to_db.text.toString()
            val year = year_auto_to_db.text.toString().toInt()
            val price_a =price_auto_to_db.text.toString().toInt()
            val procent = procent_kasko.text.toString().toInt()
            val price_total  = (price_kasko.text as String).toFloatOrNull()
            val date = SimpleDateFormat("dd/M/yyyy")
            val currentDate = date.format(Date())


            val db_id = database.reference.push().key!!
            val database= database.reference.child("kasko").child(db_id)

            val kasko = Kasko(auth.currentUser!!.uid, name, phone, model, year,
                price_a, procent,currentDate.toString(), price_total)
            database.setValue(kasko).addOnCompleteListener{
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