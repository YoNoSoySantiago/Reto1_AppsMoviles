package com.example.reto1aplication

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

public class PostsAdapter:RecyclerView.Adapter<PostViewHolder>() {
    private var posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.postrow, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postN = posts[position]
        holder.postTitleRow.text = postN.title
        holder.postAutorRow.text = postN.autor.user
        holder.postCityRow.text = postN.city
        holder.postDateRow.text = postN.date
        holder.postDescriptionRow.text = postN.description
        if(postN.autor.photo!=""){
            val uri = Uri.parse(postN.autor.photo)
            Log.e("URI",uri.toString())

            holder.postAvatarRow.setImageURI(uri)
        }

       if(postN.image!=""){

           val uri = Uri.parse(postN.image)
           Log.e("URI",uri.toString())

           holder.postImageRow.setImageURI(uri)
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
            Log.e("ERROR",json.toString())
            val array = Gson().fromJson<Array<Post>>(json.toString(),Array<Post>::class.java)
            val oldPosts = ArrayList(array.toMutableList())
            if(oldPosts.isNotEmpty()){
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
}