package com.example.carvia.list_edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.adapter.editable.KaskoEditAdapter
import com.example.carvia.insurance.db.Kasko
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DocumentListEditKasko : AppCompatActivity(), KaskoEditAdapter.ClickListener {
    private lateinit var dbref: DatabaseReference
    private lateinit var kaskoRecyclerView: RecyclerView
    private lateinit var kaskoArrayList: ArrayList<Kasko>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_list_edit_kasko)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        kaskoRecyclerView = findViewById(R.id.recycler_list_kasko_edit)
        kaskoRecyclerView.layoutManager = LinearLayoutManager(this)
        kaskoRecyclerView.setHasFixedSize(true)

        kaskoArrayList = arrayListOf<Kasko>()
        getKaskoData()
    }

    private fun getKaskoData() {
        val uid = intent.getStringExtra("UID").toString()
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("kasko")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (kaskoSnapshot in snapshot.children){
                        val kasko = kaskoSnapshot.getValue(Kasko::class.java)
                        if (kasko!!.uid.equals(uid)){
                            kaskoArrayList.add(kasko)
                        }
                    }
                    kaskoRecyclerView.adapter = KaskoEditAdapter(kaskoArrayList, this@DocumentListEditKasko)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun clickedDocs(kasko: Kasko) {
        val intent = Intent(this, SelectedDocKasko::class.java)
        intent.putExtra("Id", kasko.id)
        startActivity(intent)

    }
}