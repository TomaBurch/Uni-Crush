package com.example.findyourpair.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.findyourpair.R
import com.example.findyourpair.data.PersonInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView

    private val auth = FirebaseAuth.getInstance()
    private val personInfo = FirebaseDatabase.getInstance().getReference("User-Info")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUsername = view.findViewById(R.id.tvProfileUsername)
        tvEmail = view.findViewById(R.id.tvProfileEmail)

    }

}