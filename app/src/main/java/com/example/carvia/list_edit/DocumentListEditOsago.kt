package com.example.carvia.list_edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.adapter.editable.OsagoEdit
import com.example.carvia.insurance.db.Osago
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DocumentListEditOsago : AppCompatActivity(), OsagoEdit.ClickListener {
    private lateinit var dbref: DatabaseReference
    private lateinit var osagoRecyclerView: RecyclerView
    private lateinit var osagoArrayList: ArrayList<Osago>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_list_edit)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        osagoRecyclerView = findViewById(R.id.recycler_list_osago_edit)
        osagoRecyclerView.layoutManager = LinearLayoutManager(this)
        osagoRecyclerView.setHasFixedSize(true)

        osagoArrayList = arrayListOf<Osago>()
        getOsagoData()

    }

    private fun getOsagoData() {
        val uid = intent.getStringExtra("UID").toString()
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("osago")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (osagoSnapshot in snapshot.children){
                        val osago = osagoSnapshot.getValue(Osago::class.java)
                        if (osago!!.uid.equals(uid)){
                            osagoArrayList.add(osago)
                        }
                    }
                    osagoRecyclerView.adapter = OsagoEdit(osagoArrayList, this@DocumentListEditOsago)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun clickedDocs(osago: Osago) {
        val intent = Intent(this, SelectedDocOsago::class.java)
        intent.putExtra("Name", osago.name)
        intent.putExtra("UID", osago.uid)
        intent.putExtra("Phone", osago.phone)
        intent.putExtra("Type", osago.type_auto)
        intent.putExtra("Diagnostic", osago.diagnostic_card)
        intent.putExtra("Foreign", osago.foreign_auto)
        intent.putExtra("Exp", osago.experience)
        intent.putExtra("Per", osago.period)
        intent.putExtra("Date", osago.date_order)
        intent.putExtra("Date_end", osago.date_order_end)
        intent.putExtra("Price", osago.price.toString())
        intent.putExtra("Id", osago.id)
        startActivity(intent)

    }
}