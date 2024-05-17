package com.example.carvia.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carvia.R
import com.example.carvia.WelcomeScreen
import com.example.carvia.databinding.AccountNotificationsBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class NotificationsFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

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
        val btn = view.findViewById<MaterialButton>(R.id.btnLogout)
        btn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), WelcomeScreen::class.java))

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}