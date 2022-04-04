package com.example.reto1aplication

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

public class PostsAdapter:RecyclerView.Adapter<PostViewHolder>() {
    private var posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.postrow,parent,false)
        val postViewHolder = PostViewHolder(view)
        return postViewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postn = posts[position]
        holder.postTitleRow.text = postn.title
        holder.postAutorRow.text = postn.autor.user
        holder.postCityRow.text = postn.city
        holder.postDateRow.text = postn.date
        holder.postDescriptionRow.text = postn.description
        if(postn.autor.photo!=""){
            holder.postAvatarRow.setImageURI(Uri.parse(postn.autor.photo))
        }
        if(postn.image!=""){
            holder.postImageRow.setImageURI(Uri.parse(postn.image))
        }
    }

    fun onPause(sharedPreferences: SharedPreferences){
        val json = Gson().toJson(posts)
        Log.e(">>>>>",json.toString())

        //shared references
        sharedPreferences.edit().putString("currentPosts",json).apply()
    }

    fun onResume(sharedPreferences: SharedPreferences){
        var json = sharedPreferences.getString("currentPosts","NO_DATA")

        if(json != "NO_DATA"){
            Log.e("ERROR","WTFFFFFFFFFFFFFFFFFFFFFFF")
            Log.e("ERROR",json.toString())
            val array = Gson().fromJson<Array<Post>>(json.toString(),Array<Post>::class.java)
            val oldPosts = ArrayList(array.toMutableList())
            if(!oldPosts.isEmpty()){
                posts = oldPosts
            }
        }else{
            Log.e("ERROR","No se encuentra la serialziacion")
        }
    }
    fun addPost(post:Post){
        posts.add(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setPosts(posts:ArrayList<Post>){
        this.posts = posts
    }
}