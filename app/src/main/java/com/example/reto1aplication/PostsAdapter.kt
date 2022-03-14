package com.example.reto1aplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PostsAdapter:RecyclerView.Adapter<PostViewHolder>() {
    private val posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.postrow,parent,false)
        val postViewHolder = PostViewHolder(view)
        return postViewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postn = posts[position]
        holder.postTitleRow.text = postn.title
    }

    fun addPost(post:Post){
        posts.add(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}