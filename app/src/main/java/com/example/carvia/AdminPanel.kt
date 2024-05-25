package com.example.carvia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.adapter.AdminUserAdapter
import com.example.carvia.adapter.OsagoAdapter
import com.example.carvia.auth.Users
import com.example.carvia.insurance.db.Osago
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class AdminPanel : AppCompatActivity(), AdminUserAdapter.ClickListener {
    private lateinit var database: FirebaseDatabase

    private lateinit var auth: FirebaseAuth

    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Users>
    private lateinit var adapter:AdminUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()


        userRecyclerView = findViewById(R.id.recycler_list_user)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<Users>()
        getOsagoData()

        val back_btn: MaterialButton = findViewById(R.id.back_btn)
        back_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }




        val searchV:SearchView = findViewById(R.id.searchView)
        searchV.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }

        })



    }
    fun searchList(text:String){
        val searchList = ArrayList<Users>()
        for (user in userArrayList){
            if (user.name?.lowercase()?.contains(text.lowercase()) == true){
                searchList.add(user)
            }
        }
        userRecyclerView.adapter = AdminUserAdapter(searchList, this)
    }


    private fun getOsagoData() {
        auth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("users")
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(Users::class.java)
                        if (user != null) {
                            userArrayList.add(user)
                        }

                    }
                    userRecyclerView.adapter = AdminUserAdapter(userArrayList, this@AdminPanel)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun clickedItem(users:Users) {

        val intent = Intent(this, SelectedUser::class.java)
        intent.putExtra("Name", users.name)
        intent.putExtra("UID", users.uid)
        startActivity(intent)
        dbref = FirebaseDatabase.getInstance().getReference("users").child(users.uid.toString())

    }
}