package com.example.findyourpair.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findyourpair.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment:Fragment(R.layout.fragment_settings) {

    private lateinit var tvLogOut: TextView

    private var auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLogOut = view.findViewById(R.id.tvLogOut)

        tvLogOut.setOnClickListener {
            auth.signOut()
            SettingsFragmentDirections.actionSettingsFragmentToLoginFragment().also {
                findNavController().navigate(it)
            }

        }
    }


}