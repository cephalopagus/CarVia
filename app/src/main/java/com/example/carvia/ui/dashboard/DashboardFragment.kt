package com.example.carvia.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carvia.lists.ListProperty
import com.example.carvia.lists.ListOsago
import com.example.carvia.R
import com.example.carvia.support.SupportForm
import com.example.carvia.databinding.DocumentDashboardBinding
import com.example.carvia.lists.ListHealth
import com.example.carvia.lists.ListKasko
import com.google.android.material.button.MaterialButton

class DashboardFragment : Fragment() {

    private var _binding: DocumentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = DocumentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_osago = view.findViewById<CardView>(R.id.recycler_osago)
        btn_osago.setOnClickListener {
            startActivity(Intent(requireContext(), ListOsago::class.java))
        }
        val btn_kasko = view.findViewById<CardView>(R.id.recycler_kasko)
        btn_kasko.setOnClickListener {
            startActivity(Intent(requireContext(), ListKasko::class.java))
        }
        val btn_health = view.findViewById<CardView>(R.id.recycler_health)
        btn_health.setOnClickListener {
            startActivity(Intent(requireContext(), ListHealth::class.java))
        }


        val btn_property = view.findViewById<CardView>(R.id.recycler_property)
        btn_property.setOnClickListener {
            startActivity(Intent(requireContext(), ListProperty::class.java))
        }
        val btn_sprt = view.findViewById<MaterialButton>(R.id.support)
        btn_sprt.setOnClickListener {
            startActivity(Intent(requireContext(), SupportForm::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}