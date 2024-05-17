package com.example.carvia.ui.home

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth

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
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userEmail = user.email
            val set_name = view.findViewById<TextView>(R.id.username_homepage)
            set_name.text = userEmail
        } else {
            // No user is signed in
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}