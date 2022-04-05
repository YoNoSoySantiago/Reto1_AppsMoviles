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
import kotlin.collections.HashMap

public class PostsAdapter:RecyclerView.Adapter<PostViewHolder>() {
    private var posts = ArrayList<Post>()
    private var users = HashMap<String,User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.postrow, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postN = posts[position]

        var autor = postN.author
        holder.postTitleRow.text = postN.title
        if (autor != null) {
            holder.postAutorRow.text = autor.user
        }
        holder.postCityRow.text = postN.city
        holder.postDateRow.text = postN.date
        holder.postDescriptionRow.text = postN.description
        if (autor != null) {
            if(autor.photo!=""){
                val uri = Uri.parse(autor.photo)
                Log.e("URI",uri.toString())

                holder.postAvatarRow.setImageURI(uri)
            }
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
            if(posts.size==0){
                Log.e("ERROR",json.toString())
                val array = Gson().fromJson<Array<Post>>(json.toString(),Array<Post>::class.java)
                val oldPosts = ArrayList(array.toMutableList())
                if(oldPosts.isNotEmpty()){
                    posts = oldPosts
                }
            }
        }else{
            Log.e("ERROR","No se encuentra la serialziacion de posts")
        }

        json = sharedPreferences.getString("allUsers","NO_DATA")

        if(json != "NO_DATA"){
            Log.e("<<<",json.toString())
            val oldUsers = Gson().fromJson<HashMap<String,User>>(json.toString(),Array<Post>::class.java)

            if(oldUsers.isNotEmpty()){
                users = oldUsers
            }
        }else{
            Log.e("ERROR","No se encuentra la serialziacion de usuarios")
        }
    }
    fun addPost(post:Post){
        posts.add(post)
        Log.e("AMMOUNT",posts.size.toString())
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}