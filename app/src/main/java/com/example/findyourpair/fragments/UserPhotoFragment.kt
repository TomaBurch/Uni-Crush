package com.example.findyourpair.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.findyourpair.R

class UserPhotoFragment : Fragment(R.layout.fragment_user_photo) {

    private lateinit var iwUserPhotoAdd: ImageView
    private lateinit var btApply: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iwUserPhotoAdd = view.findViewById(R.id.iwUserPhotoAdd)
        btApply = view.findViewById(R.id.btApply)

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            iwUserPhotoAdd.setImageURI(it)
        }
        iwUserPhotoAdd.setOnClickListener {
            loadImage.launch("image/*")

        }

        btApply.setOnClickListener {
            UserPhotoFragmentDirections.actionUserPhotoFragmentToMainAppFragment().also {
                findNavController().navigate(it)
            }
        }
    }

}