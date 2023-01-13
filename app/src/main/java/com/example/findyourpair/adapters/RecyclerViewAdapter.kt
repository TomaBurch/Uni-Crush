package com.example.findyourpair.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findyourpair.R
import com.example.findyourpair.data.PersonInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RecyclerViewAdapter(private val person: MutableList<PersonInfo>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val username = itemView.findViewById<TextView>(R.id.tvUsernameItems)
        private val age = itemView.findViewById<TextView>(R.id.tvAge)
        private val uni = itemView.findViewById<TextView>(R.id.tvUni)
        private val image = itemView.findViewById<ImageView>(R.id.iwUserPhoto)
        private val iwYes = itemView.findViewById<ImageView>(R.id.iwYes)
        private val iwNo = itemView.findViewById<ImageView>(R.id.iwNo)
        private val auth = FirebaseAuth.getInstance()
        private val db = FirebaseDatabase.getInstance().getReference("User-Info")

        fun personCard(item: PersonInfo){

            username.text = item.username
            age.text = item.age.toString()
            uni.text = item.uni

            iwYes.setOnClickListener {
                db.child(auth.currentUser?.uid!!).child("connections")
                    .child("Liked")
                    .child(auth.currentUser?.uid!!)
                    .setValue(true)
                person.removeAt(adapterPosition)
            }
            iwNo.setOnClickListener {
                db.child(auth.currentUser?.uid!!).child("connections")
                    .child("Not Liked")
                    .child(auth.currentUser?.uid!!)
                    .setValue(true)
                person.removeAt(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_cards, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.personCard(person[position])
    }

    override fun getItemCount(): Int {
        return person.size
    }
}