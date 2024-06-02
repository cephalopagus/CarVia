package com.example.carvia.list_edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.adapter.PropertyAdapter
import com.example.carvia.adapter.editable.HealthEditAdapter
import com.example.carvia.adapter.editable.PropertyEditAdapter
import com.example.carvia.insurance.db.Health
import com.example.carvia.insurance.db.Property
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DocumentListEditProperty : AppCompatActivity(), PropertyEditAdapter.ClickListener {
    private lateinit var dbref: DatabaseReference
    private lateinit var propertyRecyclerView: RecyclerView
    private lateinit var propertyArrayList: ArrayList<Property>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_list_edit_property)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        propertyRecyclerView = findViewById(R.id.recycler_list_property_edit)
        propertyRecyclerView.layoutManager = LinearLayoutManager(this)
        propertyRecyclerView.setHasFixedSize(true)

        propertyArrayList = arrayListOf<Property>()
        getPropertyData()
    }

    private fun getPropertyData() {
        val uid = intent.getStringExtra("UID").toString()
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("property")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (healthSnapshot in snapshot.children){
                        val prop = healthSnapshot.getValue(Property::class.java)
                        if (prop!!.uid.equals(uid)){
                            propertyArrayList.add(prop)
                        }
                    }
                    propertyRecyclerView.adapter = PropertyEditAdapter(propertyArrayList, this@DocumentListEditProperty)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun clickedDocs(property: Property) {
        val intent = Intent(this, SelectedDocProperty::class.java)
        intent.putExtra("Id", property.id)
        startActivity(intent)

    }
}