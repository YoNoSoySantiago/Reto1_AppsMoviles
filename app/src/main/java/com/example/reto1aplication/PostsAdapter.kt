package com.example.reto1aplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

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
            val uri = Uri.parse(postn.autor.photo)
            Log.e("URI",uri.toString())

            holder.postAvatarRow.setImageURI(uri)
        }

       if(postn.image!=""){
//            val uri = Uri.parse(postn.autor.photo)
//            val bitmap = MediaStore.Images.Media.getBitmap(context, uriImage);
//            holder.postImageRow.setImageBitmap(bitmap)
           val uri = Uri.parse(postn.image)
           Log.e("URI",uri.toString())

           holder.postAvatarRow.setImageURI(uri)
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