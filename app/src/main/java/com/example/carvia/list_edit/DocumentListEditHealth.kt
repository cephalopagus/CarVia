package com.example.carvia.list_edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.adapter.editable.HealthEditAdapter
import com.example.carvia.insurance.db.Health
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DocumentListEditHealth : AppCompatActivity(), HealthEditAdapter.ClickListener {
    private lateinit var dbref: DatabaseReference
    private lateinit var healthRecyclerView: RecyclerView
    private lateinit var healthArrayList: ArrayList<Health>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_list_edit_health)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        healthRecyclerView = findViewById(R.id.recycler_list_health_edit)
        healthRecyclerView.layoutManager = LinearLayoutManager(this)
        healthRecyclerView.setHasFixedSize(true)

        healthArrayList = arrayListOf<Health>()
        getHealthData()
    }

    private fun getHealthData() {
        val uid = intent.getStringExtra("UID").toString()
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("health")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (healthSnapshot in snapshot.children){
                        val kasko = healthSnapshot.getValue(Health::class.java)
                        if (kasko!!.uid.equals(uid)){
                            healthArrayList.add(kasko)
                        }
                    }
                    healthRecyclerView.adapter = HealthEditAdapter(healthArrayList, this@DocumentListEditHealth)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun clickedDocs(health: Health) {
        val intent = Intent(this, SelectedDocHealth::class.java)
        intent.putExtra("Id", health.id)
        startActivity(intent)

    }
}