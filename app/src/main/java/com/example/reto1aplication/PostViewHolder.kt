package com.example.reto1aplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    //UI Controllers
    var posttextRow:TextView = itemView.findViewById(R.id.postTextRow)
    //State

    init {

    }
}