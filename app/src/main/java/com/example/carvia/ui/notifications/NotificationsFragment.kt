package com.example.carvia.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carvia.R
import com.example.carvia.WelcomeScreen
import com.example.carvia.adapter.OsagoAdapter
import com.example.carvia.admin.AdminPanel
import com.example.carvia.admin.Authorization
import com.example.carvia.databinding.AccountNotificationsBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NotificationsFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: OsagoAdapter

    private var _binding: AccountNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = AccountNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth= FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference.child("users").child(auth.uid.toString())
        database.get().addOnSuccessListener {
            if (it.exists()){
                val set_name = view.findViewById<TextView>(R.id.get_name)
                val name = it.child("name").value.toString()

                val set_phone = view.findViewById<TextView>(R.id.get_phone)
                val phone = it.child("phone").value.toString()

                val set_email = view.findViewById<TextView>(R.id.get_email)
                val email = it.child("email").value.toString()



                set_name.setText(name)
                set_phone.setText(phone)
                set_email.setText(email)



            }
        }



        val btn = view.findViewById<MaterialButton>(R.id.btnLogout)
        btn.setOnClickListener {

            auth.signOut()
            startActivity(Intent(requireContext(), WelcomeScreen::class.java))

        }

        val admin_btn: MaterialButton = view.findViewById(R.id.btn_admin_panel)

        admin_btn.setOnClickListener {
            startActivity(Intent(context, Authorization::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}