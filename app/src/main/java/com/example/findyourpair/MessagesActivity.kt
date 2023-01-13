package com.example.findyourpair

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findyourpair.adapters.MessageAdapter
import com.example.findyourpair.databinding.ActivityMessagesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MessagesActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMessagesBinding

    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageKeyList: ArrayList<String>
    private lateinit var messageAdapter: MessageAdapter

    private val db = FirebaseDatabase.getInstance().getReference("Chat")
    private val senderUid = FirebaseAuth.getInstance().currentUser?.uid!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        val messageRecyclerView = binding.recycler
        val messagebox = binding.messageBox
        val sendButton: ImageView = binding.sendImage
        val replyText = binding.replyText
        val replyReset = binding.resetReply
        val responseLayout = binding.responseLayout

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter

        //headerInfo

        //messages
        messageKeyList = ArrayList()
        db.child("chat").child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    messageKeyList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue((Message::class.java))
                        val messageKey = postSnapshot.key
                        messageList.add((message!!))
                        messageKeyList.add(messageKey!!)

                    }
                    val message = messageList.last()
                    val builder = NotificationCompat.Builder(applicationContext, "1")
                        .setSmallIcon(R.drawable.ic_baseline_sms_24)
                        .setContentTitle(message.sendername)
                        .setContentText(message.message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                    if (senderUid != message.senderId) {
                        with(NotificationManagerCompat.from(applicationContext)) {
                            notify(1, builder.build())
                        }
                    }
                    messageAdapter.notifyDataSetChanged()
                    messageRecyclerView.scrollToPosition(messageAdapter.itemCount - 1)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        var responseMessage = ""

        messageAdapter.setOnItemClickListener(object : MessageAdapter.OnItemClickListener {
            override fun onItemClick(position: Int): String {
                responseMessage = messageList[position].message.toString()
                responseLayout.visibility = View.VISIBLE
                replyText.text = responseMessage
                return responseMessage
            }
        })
        //response cancel
        replyReset.setOnClickListener {
            responseMessage = ""
            responseLayout.visibility = View.GONE
            replyText.text = responseMessage
        }

        sendButton.setOnClickListener {
            val message = messagebox.text.toString()
            var name: String
            if (message != "") {
                FirebaseDatabase.getInstance().getReference("user").child(senderUid).get()
                    .addOnSuccessListener {
                        if (it.exists()) {
                            val username = it.child("name").value
                            name = username.toString()

                            val messageObject = Message(message, senderUid, name, responseMessage)

                            db.child("chat").child("messages").push()
                                .setValue(messageObject)
                            messagebox.setText("")
                            responseMessage = ""
                            responseLayout.visibility = View.GONE
                        }
                    }
            }
        }
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "message"
            val descriptionText = "MessageNotification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}