package com.example.carvia.lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.adapter.PropertyAdapter
import com.example.carvia.insurance.db.Property
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListProperty : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var propertyRecyclerView: RecyclerView
    private lateinit var propertyArrayList: ArrayList<Property>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_property)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        propertyRecyclerView = findViewById(R.id.recycler_list_property)
        propertyRecyclerView.layoutManager = LinearLayoutManager(this)
        propertyRecyclerView.setHasFixedSize(true)

        propertyArrayList = arrayListOf<Property>()
        getPropertyData()
    }

    private fun getPropertyData() {
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("property")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (propertySnapshot in snapshot.children){
                        val property = propertySnapshot.getValue(Property::class.java)
                        if (property!!.uid.equals(auth.currentUser!!.uid)){
                            propertyArrayList.add(property)
                        }
                    }
                    propertyRecyclerView.adapter = PropertyAdapter(propertyArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}