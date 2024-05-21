package com.example.carvia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.adapter.OsagoAdapter
import com.example.carvia.insurance.db.Osago
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ListOsago : AppCompatActivity() {


    private lateinit var dbref:DatabaseReference
    private lateinit var osagoRecyclerView: RecyclerView
    private lateinit var osagoArrayList: ArrayList<Osago>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_osago)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        osagoRecyclerView = findViewById(R.id.recycler_list_osago)
        osagoRecyclerView.layoutManager = LinearLayoutManager(this)
        osagoRecyclerView.setHasFixedSize(true)

        osagoArrayList = arrayListOf<Osago>()
        getOsagoData()

    }

    private fun getOsagoData() {
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("osago")
        dbref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (osagoSnapshot in snapshot.children){
                        val osago = osagoSnapshot.getValue(Osago::class.java)
                        if (osago!!.uid.equals(auth.currentUser!!.uid)){
                            osagoArrayList.add(osago)
                        }
                    }
                    osagoRecyclerView.adapter = OsagoAdapter(osagoArrayList)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}