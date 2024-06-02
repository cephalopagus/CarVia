package com.example.carvia.lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.adapter.HealthAdapter
import com.example.carvia.adapter.KaskoAdapter
import com.example.carvia.insurance.db.Health
import com.example.carvia.insurance.db.Kasko
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListHealth : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var healthRecyclerView: RecyclerView
    private lateinit var healthArrayList: ArrayList<Health>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_health)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        healthRecyclerView = findViewById(R.id.recycler_list_health)
        healthRecyclerView.layoutManager = LinearLayoutManager(this)
        healthRecyclerView.setHasFixedSize(true)

        healthArrayList = arrayListOf<Health>()
        getHealthData()
    }

    private fun getHealthData() {
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("health")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (healthSnapshot in snapshot.children){
                        val health = healthSnapshot.getValue(Health::class.java)
                        if (health!!.uid.equals(auth.currentUser!!.uid)){
                            healthArrayList.add(health)
                        }
                    }
                    healthRecyclerView.adapter = HealthAdapter(healthArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}