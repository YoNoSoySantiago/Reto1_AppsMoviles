package com.example.reto1aplication

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reto1aplication.databinding.FragmentNewHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NewHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewHomeFragment : Fragment(),NewPostFragment.OnNewPostListener {

    private var _binding: FragmentNewHomeBinding?=null
    private val binding get() =_binding!!


    //STATE
    private var adapter = PostsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentNewHomeBinding.inflate(inflater,container, false)
        val view = binding.root

        //recrear el estado
        val postRecycler = binding.postRecycler
        postRecycler.setHasFixedSize(true)
        postRecycler.layoutManager = LinearLayoutManager(activity)

        postRecycler.adapter = adapter

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        context?.contentResolver?.let { adapter.onResume(sharedPreferences, it) }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = NewHomeFragment()
    }

    override fun onNewPost(
        id:String,
        title:String,
        author:User,
        city:String,
        date:String,
        description:String,
        image: String
    ) {
        val newPost = Post(id,title,author,city,date,description,image)
        adapter.addPost(newPost)
    }

    override fun onPause() {
        super.onPause()

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        adapter.onPause(sharedPreferences)
    }

}