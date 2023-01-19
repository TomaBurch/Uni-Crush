package com.example.findyourpair.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourpair.R
import com.example.findyourpair.adapters.RecyclerViewAdapter
import com.example.findyourpair.data.PersonInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private lateinit var rvRecycle: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter

    private val userDb = FirebaseDatabase.getInstance().getReference("User-Info")
    private val auth = FirebaseAuth.getInstance()
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid!!
    private val personCard = ArrayList<PersonInfo>()
    private var sex:String = ""
    private var oppositeSex:String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserSex()

        rvRecycle = view.findViewById(R.id.rvCards)
        adapter = RecyclerViewAdapter(personCard)
        rvRecycle.adapter = adapter

        val myLinearLayoutManager = object : LinearLayoutManager(context){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        rvRecycle.layoutManager = myLinearLayoutManager

    }
    fun checkUserSex(){
        val user = userDb.child(auth.currentUser?.uid!!)
        user.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("sex").value != null){
                        sex = snapshot.child("sex").value.toString()
                        when(sex){
                            "Male" -> oppositeSex = "Female"
                            "Female" -> oppositeSex = "Male"
                        }
                    }
                    getOppositeSexUser()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun getOppositeSexUser() {
        userDb.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.child("sex").value != null){
                    if(snapshot.exists() && !snapshot.child("connections").child("Liked").hasChild(currentUserId)
                        && !snapshot.child("connections").child("Not Liked").hasChild(currentUserId) && snapshot.child("sex").value.toString().equals(oppositeSex)){
                        val profileImageUri = "default"
                        val item = PersonInfo(
                            snapshot.child("name").value.toString(),
                            snapshot.child("age").value.toString(),
                            snapshot.child("uni").value.toString(),
                            snapshot.key.toString(),
                            profileImageUri
                        )
                        personCard.add(item)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}