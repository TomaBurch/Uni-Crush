package com.example.findyourpair.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findyourpair.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationFragment:Fragment(R.layout.fragment_registration){

    private lateinit var etEmail: TextInputLayout
    private lateinit var etPassword:TextInputLayout
    private lateinit var etConfirmPassword:TextInputLayout
    private lateinit var etAge:TextInputLayout
    private lateinit var etUni:TextInputLayout
    private lateinit var btRegister: Button
    private lateinit var tvToLogin: TextView
    private lateinit var etUsername: TextInputLayout

    private var auth  = FirebaseAuth.getInstance()
    private var personInfo = FirebaseDatabase.getInstance().getReference("User-Info")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        etAge = view.findViewById(R.id.etAge)
        etUni = view.findViewById(R.id.etUni)
        btRegister = view.findViewById(R.id.btRegister)
        tvToLogin = view.findViewById(R.id.tvToLogin)
        etUsername = view.findViewById(R.id.etUsername)

        btRegister.setOnClickListener {

            val email = etEmail.editText?.text.toString()
            val password = etPassword.editText?.text.toString()
            val confirmPassword = etConfirmPassword.editText?.text.toString()
            val username = etUsername.editText?.text.toString()
            val age = etAge.editText?.text.toString()
            val uni = etUni.editText?.text.toString()


            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

                Toast.makeText(context, "Password and Email shouldn't be empty!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener

            } else if (password != confirmPassword) {
                Toast.makeText(context, "Type password correctly", Toast.LENGTH_LONG).show()
            } else if (username.length >= 11) {
                Toast.makeText(context, "Username is too long", Toast.LENGTH_LONG).show()
            } else if (username.length <= 2) {
                Toast.makeText(context, "Username is too short", Toast.LENGTH_LONG).show()
            }else if(uni.any{ it.isDigit() }){
                Toast.makeText(context, "University should not contain any digits", Toast.LENGTH_SHORT).show()
            }else if (password.length <= 8) {
                Toast.makeText(context, "Password is too short", Toast.LENGTH_LONG).show()
            } else if (!(password.any { it.isDigit() }) ){
                Toast.makeText(context, "Password must have at least one digit", Toast.LENGTH_LONG).show()
            } else {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            /*FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                                ?.addOnSuccessListener {*/

                                    personInfo.child(auth.currentUser?.uid!!).child("name").setValue(username)
                                    personInfo.child(auth.currentUser?.uid!!).child("email").setValue(email)
                                    personInfo.child(auth.currentUser?.uid!!).child("age").setValue(age)
                                    personInfo.child(auth.currentUser?.uid!!).child("uni").setValue(uni)
                                    RegistrationFragmentDirections.actionRegistrationFragmentToUserPhotoFragment().also {
                                        findNavController().navigate(it)
                                    }
                                //}
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Something went wrong, try again later", Toast.LENGTH_LONG).show()
                    }
            }
        }
        tvToLogin.setOnClickListener {

            RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment().also {
                findNavController().navigate(it)
            }

        }
    }
}
