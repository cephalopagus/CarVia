package com.example.carvia.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.santalu.maskara.widget.MaskEditText

class Registration : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private  val emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        //косметика
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        //


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        val reg_name:TextInputEditText = findViewById(R.id.full_name_input)
        val reg_email:TextInputEditText = findViewById(R.id.email_input)
        val reg_phone_num:MaskEditText = findViewById(R.id.phone_num_input)
        val reg_password:TextInputEditText = findViewById(R.id.password_input)
        val reg_password_rep:TextInputEditText = findViewById(R.id.repeat_password_input)
        val conf_btn:MaterialButton = findViewById(R.id.complete_btn)


        conf_btn.setOnClickListener {
            val name = reg_name.text.toString()
            val phone = reg_phone_num.text.toString()
            val email = reg_email.text.toString()
            val password = reg_password.text.toString()
            val r_password = reg_password_rep.text.toString()

            if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()||r_password.isEmpty()){
                if(name.isEmpty()){
                    reg_name.error="Введите свое имя"
                }
                if(email.isEmpty()){
                    reg_email.error="Введите коректно email адрес"
                }
                if (phone.isEmpty()){
                    reg_phone_num.error="Введите номер телефона"
                }
                if(password.isEmpty()){
                    reg_password.error="Пароль не должен быть пустым"
                }
                if (r_password.isEmpty()){
                    reg_password_rep.error="Пароли не совпадают"
                }
                Toast.makeText(this,"Что то пошло не так",Toast.LENGTH_LONG).show()
            }
            else if (!email.matches(emailPattern.toRegex())){
                reg_name.error="Не корестный email адрес"
            }
            else if (phone.length==10){
                reg_phone_num.error="Номер телефона не коректный"
                Toast.makeText(this,"Номер телефона не коректный",Toast.LENGTH_LONG).show()
            }
            else if(password.length<6){
                reg_password.error="Пароль должен быть больше 6 символов"
                Toast.makeText(this,"Пароль должен быть больше 6 символов",Toast.LENGTH_LONG).show()

            }
            else if(password!=r_password){
                reg_password_rep.error="Пароль не совпадает"
                Toast.makeText(this,"Пароль не совпадает",Toast.LENGTH_LONG).show()

            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val database=
                            database.reference.child("users").child(auth.currentUser!!.uid)
                        val users:Users=Users(name,email,phone,auth.currentUser!!.uid)
                        database.setValue(users).addOnCompleteListener {
                            if(it.isSuccessful){
                                val intent= Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this,"Что то пошло не так ,попробуйте снова", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }
}