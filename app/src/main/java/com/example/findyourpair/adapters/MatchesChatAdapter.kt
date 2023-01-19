package com.example.findyourpair.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findyourpair.MainActivity
import com.example.findyourpair.MessagesActivity
import com.example.findyourpair.R
import com.example.findyourpair.data.MatchesObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MatchesChatAdapter(private var personChat: MutableList<MatchesObject>,val context: Context): RecyclerView.Adapter<MatchesChatAdapter.PersonChatViewHolder>() {

    inner class PersonChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private var iwPersonChatImage = itemView.findViewById<ImageView>(R.id.iwPersonChatImage)
        private var tvPersonChatName = itemView.findViewById<TextView>(R.id.tvPersonChatName)
        private val db = FirebaseDatabase.getInstance().getReference("User-Info")
        private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!

        fun personChat(item:MatchesObject){
            tvPersonChatName.text = item.getUsername()
            Glide.with(itemView)
                .load(item.getImageUrl())
                .into(iwPersonChatImage)
            //itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Intent(context, MessagesActivity::class.java).also {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonChatViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_matches_chat, parent, false)
        return PersonChatViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: PersonChatViewHolder, position: Int) {
        return holder.personChat(personChat[position])
    }

    override fun getItemCount(): Int {
        return personChat.size
    }
}