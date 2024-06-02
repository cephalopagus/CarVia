package com.example.carvia.insurance

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.example.carvia.insurance.db.Osago
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class CreatingInsuranceOsago : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_insurance)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val typeAuto = arrayOf("Легковое авто - до 2.0 л",
            "Легковое авто - от 2.0 до 3.0 л",
            "Легковое авто - до 3.0 л",
            "Электромобиль - до 50 кВт/ч",
            "Электромобиль - от 51 кВт/ч",
            "Автобусы - до 16 пассажирских мест",
            "Автобусы - от 16 пассажирских мест")

        val expAuto = arrayOf("До 25 лет, стаж до 3х лет",
            "До 25 лет, стаж от 3х лет",
            "От 25 лет, стаж до 3х лет",
            "От 25 лет, стаж от 3х лет",
            "Без ограничения по количеству водителей/юридические лица")

        val perAuto = arrayOf("От 5 до 15 дней",
            "От 16 дней до 1 месяца",
            "До 3 месяцев",
            "До 6 месяцев",
            "До 9 месяцев",
            "До 12 месяцев")



        val autoComplete:AutoCompleteTextView = findViewById(R.id.auto_type_osago)
        val adapter = ArrayAdapter(this, R.layout.dropdown_list,typeAuto)
        autoComplete.setAdapter(adapter)


        val name_to_db:TextInputEditText = findViewById(R.id.name_osago)
        val phone_to_db:TextInputEditText = findViewById(R.id.phone_osago)
        val type_auto_to_db:AutoCompleteTextView = findViewById(R.id.auto_type_osago)
        val diagnostic_card_to_bd:RadioGroup = findViewById(R.id.diagnostic_card_osago)
        val foreign_auto_to_db:RadioGroup = findViewById(R.id.foreign_auto_osago)
        val experience_to_db:RadioGroup = findViewById(R.id.experience_osago)
        val period_to_db:RadioGroup = findViewById(R.id.period_osago)
        val btn_osago:MaterialButton = findViewById(R.id.btn_create_osago)
        val btn_cal_osago:MaterialButton = findViewById(R.id.btn_calculate_osago)
        var price_osago:TextView = findViewById(R.id.price_osago)
        price_osago.text = "0.0"
        var price=0f
        var coefficient = 150f


        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        database.reference.child("users").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                val set_name = findViewById<TextView>(R.id.name_osago)
                val name = it.child("name").value.toString()
                val set_phone = findViewById<TextView>(R.id.phone_osago)
                val phone = it.child("phone").value.toString()
                set_name.setText(name)
                set_phone.setText(phone)

            }
        }


        btn_cal_osago.setOnClickListener {
            val experience_rb:RadioButton = findViewById(experience_to_db.checkedRadioButtonId)
            val period_rb:RadioButton = findViewById(period_to_db.checkedRadioButtonId)
            price=500f
            price_osago.setText("")
            val index_type = typeAuto.indexOf(type_auto_to_db.text.toString())
            price+=coefficient*(index_type+1)
            if (findViewById<RadioButton>(foreign_auto_to_db.checkedRadioButtonId).text.toString() == "Да"){
                price+=700f
            }else{
                price=price
            }
            val index_exp = expAuto.indexOf(experience_rb.text.toString())
            price+=coefficient*((index_exp+1.5f)/2)
            val index_per = perAuto.indexOf(period_rb.text.toString())
            price+=coefficient*((index_per+1.6f)/2)
            if (findViewById<RadioButton>(diagnostic_card_to_bd.checkedRadioButtonId).text.toString() == "Имеется"){
                price-=400
            }else{
                price=price
            }
            price_osago.setText(price.toString())
        }


        btn_osago.setOnClickListener {

            val foreign_auto_rb:RadioButton = findViewById(foreign_auto_to_db.checkedRadioButtonId)
            val experience_rb:RadioButton = findViewById(experience_to_db.checkedRadioButtonId)
            val period_rb:RadioButton = findViewById(period_to_db.checkedRadioButtonId)
            val diagnostic_rb:RadioButton = findViewById(diagnostic_card_to_bd.checkedRadioButtonId)

            val name = name_to_db.text
            val phone = phone_to_db.text
            val type = type_auto_to_db.text
            val foreign = foreign_auto_rb.text
            val exp = experience_rb.text
            val period = period_rb.text
            val diagnostic = diagnostic_rb.text
            val price_total  = (price_osago.text as String).toFloatOrNull()
            val currentDate = LocalDate.now()

            val perMap = mapOf("От 5 до 15 дней" to 15,
                "От 16 дней до 1 месяца" to 31,
                "До 3 месяцев" to 91,
                "До 6 месяцев" to 182,
                "До 9 месяцев" to 273,
                "До 12 месяцев" to 365)

            val index = perMap.get((period))
            val end_date = currentDate.plusDays(index!!.toLong())


            val db_id = database.reference.push().key!!
            val database= database.reference.child("osago").child(db_id)
            val osago = Osago(auth.currentUser!!.uid, name.toString(), phone.toString(),
                type.toString(),diagnostic.toString(), foreign.toString(),
                exp.toString(), period.toString(),currentDate.toString(), end_date.toString(), price_total, db_id)
            database.setValue(osago).addOnCompleteListener{
                Toast.makeText(this,"Документ оформлен!", Toast.LENGTH_LONG).show()
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"Что то пошло не так ,попробуйте снова", Toast.LENGTH_LONG).show()
            }
        }
    }
}