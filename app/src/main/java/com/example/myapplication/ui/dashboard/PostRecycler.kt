package com.example.myapplication.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.firebase.firestore.DocumentSnapshot

class PostRecycler (private val Post: List<DocumentSnapshot>): RecyclerView.Adapter<PostRecycler.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.otherUserPostText)
        val messageButton = itemView.findViewById<Button>(R.id.commentButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostRecycler.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val timeLineView = inflater.inflate(R.layout.post, parent, false)

        return ViewHolder(timeLineView)
    }

    override fun onBindViewHolder(viewHolder: PostRecycler.ViewHolder, position: Int) {
        // Get the data model based on position
        val post = Post.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.nameTextView
        textView.text = post.get("postName").toString()
        val button = viewHolder.messageButton
        button.text = "Comment"
    }

    override fun getItemCount(): Int {
        return Post.size
    }

}
