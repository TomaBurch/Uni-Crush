package com.example.findyourpair.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.findyourpair.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UserPhotoFragment : Fragment(R.layout.fragment_user_photo) {

    private lateinit var iwUserPhotoAdd: ImageView
    private lateinit var btApply: Button

    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!
    private val db = FirebaseDatabase.getInstance().getReference("User-Info")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iwUserPhotoAdd = view.findViewById(R.id.iwUserPhotoAdd)
        btApply = view.findViewById(R.id.btApply)



         /*val loadImage : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result != null) {
                    // getting URI of selected Image
                    val imageUri: Uri? = result.data?.data
                    // val fileName = imageUri?.pathSegments?.last()
                    // extract the file name with extension
                    val sd = getFileName(requireContext().applicationContext, imageUri!!)

                    // Upload Task with upload to directory 'file'
                    // and name of the file remains same
                    val uploadTask = FirebaseStorage.getInstance().getReference("file/$sd").putFile(imageUri)

                    // On success, download the file URL and display it
                    uploadTask.addOnSuccessListener {
                        db.child(currentUserId).child("imageUri").setValue(imageUri)
                        // using glide library to display the image
                        FirebaseStorage.getInstance()
                            .getReference("file/$sd").downloadUrl.addOnSuccessListener {
                                Glide.with(requireContext().applicationContext)
                                    .load(it)
                                    .into(iwUserPhotoAdd)

                            }
                    }
                }
            }
        iwUserPhotoAdd.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image"
            loadImage.launch(galleryIntent)
        }*/


        btApply.setOnClickListener {
            UserPhotoFragmentDirections.actionUserPhotoFragmentToMainAppFragment().also {
                findNavController().navigate(it)
            }
        }
    }

    /*private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME).toInt())
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }*/

}