package com.example.findyourpair.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourpair.R
import com.example.findyourpair.adapters.MatchesChatAdapter
import com.example.findyourpair.data.MatchesObject
import com.example.findyourpair.data.PersonInfo
import com.example.findyourpair.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var rvChat:RecyclerView
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!
    private var resultMatches = ArrayList<MatchesObject>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvChat = view.findViewById(R.id.rvChat)

        val adapter = MatchesChatAdapter(getDataSetMatches(), requireContext().applicationContext)
        rvChat.adapter = adapter
        rvChat.layoutManager = LinearLayoutManager(context)

        getUserMatchId()
    }

    private fun getUserMatchId() {
        val matchDb = FirebaseDatabase.getInstance().getReference("User-Info").child(currentUserId)
            .child("connections")
            .child("matches")
        matchDb.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(match in snapshot.children){
                        fetchMatchInformation(match.key!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fetchMatchInformation(key: String) {
        val userDb = FirebaseDatabase.getInstance().getReference("User-Info").child(key)
        userDb.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var userId = snapshot.key
                    var name = ""
                    var profileImageUrl = ""
                    if(snapshot.child("name").value != null){
                        name = snapshot.child("name").value.toString()
                    }
                    if(snapshot.child("profileImageUrl").value != null){
                        profileImageUrl = snapshot.child("profileImageUrl").value.toString()
                    }
                    var obj = MatchesObject(name, userId.toString(), profileImageUrl)
                    resultMatches.add(obj)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getDataSetMatches(): MutableList<MatchesObject> {
        return resultMatches
    }
}
