package com.example.findyourpair.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourpair.Message
import com.example.findyourpair.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,private val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2
    private val ITEM_RESPONSE = 3
    private val ITEM_RESPONSE_RECEIVE = 4

    private lateinit var messageListener: OnItemClickListener
    private lateinit var messageKeyList: ArrayList<String>

    interface OnItemClickListener {
        fun onItemClick(position: Int): String?
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        messageListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
                ReceiveViewHolder(view)
            }
            2 -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
                SentViewHolder(view)
            }
            3 -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.response, parent, false)
                SentResponseViewHolder(view)
            }
            else -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.responce_received, parent, false)
                ReceiveResponseViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (holder.javaClass) {
            SentViewHolder::class.java -> {
                val holder = holder as SentViewHolder
                holder.sentMessage.text = currentMessage.message
            }
            ReceiveViewHolder::class.java -> {
                val holder = holder as ReceiveViewHolder
                holder.receiveMessage.text = currentMessage.message
                holder.name.text = currentMessage.sendername.toString()
            }
            SentResponseViewHolder::class.java -> {
                val holder = holder as SentResponseViewHolder
                holder.sentMessage.text = currentMessage.message
                holder.responseToMsg.text = currentMessage.responsetext
            }
            else -> {
                val holder = holder as ReceiveResponseViewHolder
                holder.receiveMessage.text = currentMessage.message
                holder.name.text = currentMessage.sendername.toString()
                holder.respondingToMsg.text = currentMessage.responsetext
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            if (currentMessage.responsetext == "") {
                ITEM_SENT
            } else {
                ITEM_RESPONSE
            }
        }else {
            if (currentMessage.responsetext == "") {
                ITEM_RECEIVE
            }else {
                ITEM_RESPONSE_RECEIVE
            }
        }
    }

    class SentResponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_response)
        val responseToMsg = itemView.findViewById<TextView>(R.id.txt_recceived_response)

    }
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_received)
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    class ReceiveResponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_received_recres)
        val name = itemView.findViewById<TextView>(R.id.name_response)
        var respondingToMsg = itemView.findViewById<TextView>(R.id.txt_sent_recres)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}
