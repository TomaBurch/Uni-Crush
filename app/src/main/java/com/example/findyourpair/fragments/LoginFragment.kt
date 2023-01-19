package com.example.findyourpair.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findyourpair.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var etEmail: TextInputLayout
    private lateinit var etPassword:TextInputLayout
    private lateinit var btLogin: Button
    private lateinit var tvToRegister: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (FirebaseAuth.getInstance().currentUser != null) {

            LoginFragmentDirections.actionLoginFragmentToMainAppFragment().also { findNavController().navigate(it) }

        }
        super.onViewCreated(view, savedInstanceState)

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btLogin = view.findViewById(R.id.btLogin)
        tvToRegister = view.findViewById(R.id.tvToRegister)

        btLogin.setOnClickListener {
            val email = etEmail.editText?.text.toString()
            val password = etPassword.editText?.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Password and Email shouldn't be empty!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        LoginFragmentDirections.actionLoginFragmentToMainAppFragment().also {
                            findNavController().navigate(it)
                        }

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
        }
        tvToRegister.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToRegistrationFragment().also {
                findNavController().navigate(it)
            }

        }
    }
}