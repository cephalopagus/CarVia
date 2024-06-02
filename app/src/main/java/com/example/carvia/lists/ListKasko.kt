package com.example.carvia.lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.adapter.KaskoAdapter
import com.example.carvia.adapter.OsagoAdapter
import com.example.carvia.insurance.db.Kasko
import com.example.carvia.insurance.db.Osago
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListKasko : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var kaskoRecyclerView: RecyclerView
    private lateinit var kaskoArrayList: ArrayList<Kasko>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kasko)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        kaskoRecyclerView = findViewById(R.id.recycler_list_kasko)
        kaskoRecyclerView.layoutManager = LinearLayoutManager(this)
        kaskoRecyclerView.setHasFixedSize(true)

        kaskoArrayList = arrayListOf<Kasko>()
        getKaskoData()
    }

    private fun getKaskoData() {
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("kasko")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (kaskoSnapshot in snapshot.children){
                        val kasko = kaskoSnapshot.getValue(Kasko::class.java)
                        if (kasko!!.uid.equals(auth.currentUser!!.uid)){
                            kaskoArrayList.add(kasko)
                        }
                    }
                    kaskoRecyclerView.adapter = KaskoAdapter(kaskoArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}