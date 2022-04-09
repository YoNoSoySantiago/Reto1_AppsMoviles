package com.example.reto1aplication

import android.content.ContentResolver
import android.content.SharedPreferences
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PostsAdapter:RecyclerView.Adapter<PostViewHolder>() {
    private var posts = ArrayList<Post>()
    private var users = HashMap<String,User>()
    private lateinit var contentResolver:ContentResolver
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.postrow, parent, false)
        return PostViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postN = posts[position]

        var autor = postN.author
        holder.postTitleRow.text = postN.title
        holder.postAutorRow.text = autor.user
        holder.postCityRow.text = postN.city
        holder.postDateRow.text = postN.date
        holder.postDescriptionRow.text = postN.description
        if(autor.photo!=""){
            val uri = Uri.parse(autor.photo)
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
            val thumbnail = getCircularBitmap(bitmap)
            holder.postAvatarRow.setImageBitmap(thumbnail)
        }

       if(postN.image!=""){
           val uri = Uri.parse(postN.image)
           holder.postImageRow.setImageURI(uri)
       }
    }

    fun onPause(sharedPreferences: SharedPreferences){
        val json = Gson().toJson(posts)
        //shared references
        sharedPreferences.edit().putString("currentPosts",json).apply()
    }

    fun onResume(sharedPreferences: SharedPreferences,contentResolver: ContentResolver){
        var json = sharedPreferences.getString("currentPosts","NO_DATA")
        this.contentResolver = contentResolver
        if(json != "NO_DATA"){
            if(posts.size==0){
                val array = Gson().fromJson(json.toString(),Array<Post>::class.java)
                val oldPosts = ArrayList(array.toMutableList())
                if(oldPosts.isNotEmpty()){
                    posts = oldPosts
                }
            }
        }

        json = sharedPreferences.getString("allUsers","NO_DATA")

        if(json != "NO_DATA"){
            val type: Type = object : TypeToken<HashMap<String, User>>() {}.type
            users =  Gson().fromJson(json, type)

        }
    }
    fun addPost(post:Post){
        posts.add(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCircularBitmap(srcBitmap: Bitmap?): Bitmap {

        // Select whichever of width or height is minimum
        val squareBitmapWidth = Integer.min(srcBitmap!!.width, srcBitmap.height)

        // Generate a bitmap with the above value as dimensions
        val dstBitmap = Bitmap.createBitmap(
            squareBitmapWidth,
            squareBitmapWidth,
            Bitmap.Config.ARGB_8888
        )

        // Initializing a Canvas with the above generated bitmap
        val canvas = Canvas(dstBitmap)

        // initializing Paint
        val paint = Paint()
        paint.isAntiAlias = true

        // Generate a square (rectangle with all sides same)
        val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
        val rectF = RectF(rect)

        // Operations to draw a circle
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val left = ((squareBitmapWidth - srcBitmap.width) / 2).toFloat()
        val top = ((squareBitmapWidth - srcBitmap.height) / 2).toFloat()
        canvas.drawBitmap(srcBitmap, left, top, paint)
        srcBitmap.recycle()

        // Return the bitmap
        return dstBitmap
    }
}