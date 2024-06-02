package com.example.carvia.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carvia.insurance.CreatingInsuranceHealth
import com.example.carvia.insurance.CreatingInsuranceOsago
import com.example.carvia.insurance.CreatingInsuranceProperty
import com.example.carvia.insurance.CreatingInsuranseKasko
import com.example.carvia.R
import com.example.carvia.databinding.FragmentHomeBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_osago = view.findViewById<CardView>(R.id.action_create_insurance_osago)
        btn_osago.setOnClickListener {
            startActivity(Intent(requireContext(), CreatingInsuranceOsago::class.java))
        }
        val btn_kasko = view.findViewById<CardView>(R.id.action_create_insurance_kasko)
        btn_kasko.setOnClickListener {
            startActivity(Intent(requireContext(), CreatingInsuranseKasko::class.java))
        }
        val btn_health = view.findViewById<CardView>(R.id.action_create_insurance_health)
        btn_health.setOnClickListener {
            startActivity(Intent(requireContext(), CreatingInsuranceHealth::class.java))
        }
        val btn_property = view.findViewById<CardView>(R.id.action_create_insurance_property)
        btn_property.setOnClickListener {
            startActivity(Intent(requireContext(), CreatingInsuranceProperty::class.java))
        }
        val auth = FirebaseAuth.getInstance().currentUser
        var database = FirebaseDatabase.getInstance().reference.child("users").child(auth!!.uid)
        database.get().addOnSuccessListener {
            if (it.exists()){
                val set_name = view.findViewById<TextView>(R.id.username_homepage)
                val name = it.child("name").value.toString()
                val name_ms = name.split(" ")
                if (name_ms[1].equals("уулу") || name_ms[1].equals("кызы")){

                    set_name.append(name_ms[2]+"!")
                }
                else{
                    set_name.append(name_ms[1]+"!")
                }

            }
        }

        val btn_call = view.findViewById<MaterialButton>(R.id.call_im)
        btn_call.setOnClickListener {
            val call = Intent(Intent.ACTION_DIAL)
            call.data = Uri.parse("tel:+996773428201")
            startActivity(call)
        }

//        if (auth != null) {
//            val userEmail = auth.email
//            val set_name = view.findViewById<TextView>(R.id.username_homepage)
//            set_name.text = userEmail
//        } else {
//            // No user is signed in
//        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}