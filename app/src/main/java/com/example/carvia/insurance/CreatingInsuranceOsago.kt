package com.example.carvia.insurance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.carvia.R

class CreatingInsuranceOsago : AppCompatActivity() {
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

        val autoComplete:AutoCompleteTextView = findViewById(R.id.dropdown_menu)
        val adapter = ArrayAdapter(this, R.layout.dropdown_list,typeAuto)
        autoComplete.setAdapter(adapter)

    }
}