package com.example.carvia.insurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.example.carvia.admin.AdminPanel
import com.example.carvia.insurance.db.Card
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.santalu.maskara.widget.MaskEditText

class Payment : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val pr = intent.getStringExtra("Price")


        val btn_pay:MaterialButton = findViewById(R.id.create_payment)
        val price_pay:TextView = findViewById<TextView?>(R.id.price_payment)
        val card_num:MaskEditText = findViewById(R.id.card_num)
        val card_year:MaskEditText = findViewById(R.id.card_mmyy)
        val card_cvv: TextInputEditText = findViewById(R.id.card_cvv)
        val card_name:TextInputEditText = findViewById(R.id.card_name)
        val switch:Switch = findViewById(R.id.switch_rn)
        price_pay.setText(pr.toString())


        database.reference.child("payment").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                val card = it.child("card_num").value.toString()
                val card_y = it.child("card_year").value.toString()
                val card_c = it.child("card_cvv").value.toString()
                val card_n = it.child("card_name").value.toString()

                card_num.setText(card)
                card_year.setText(card_y)
                card_cvv.setText(card_c)
                card_name.setText(card_n)
            }
        }
        btn_pay.setOnClickListener {
            if (switch.isChecked){
                val price = (price_pay.text as String).toFloatOrNull()
                val cardNum = card_num.text.toString()
                val cardYear = card_year.text.toString()
                val cardCVV = card_cvv.text.toString()
                val cardName = card_name.text.toString()
                val database= database.reference.child("payment").child(auth.currentUser!!.uid)
                val card:Card = Card(cardName, cardNum, cardYear, cardCVV)
                database.setValue(card).addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this,"Документ оформлен!", Toast.LENGTH_LONG).show()
                }.addOnCanceledListener {
                    Toast.makeText(this,"Ошибка!", Toast.LENGTH_LONG).show()
                }
            }else if (!switch.isChecked){
                database.reference.child("payment").child(auth.currentUser!!.uid).removeValue().
                addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Документ оформлен!", Toast.LENGTH_LONG).show()
                }.addOnCanceledListener {
                    Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}