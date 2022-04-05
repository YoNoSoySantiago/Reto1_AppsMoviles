package com.example.reto1aplication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reto1aplication.databinding.FragmentNewHomeBinding
import com.google.gson.Gson
import java.io.File

/**
 * A simple [Fragment] subclass.
 * Use the [NewHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewHomeFragment : Fragment(),NewPostFragment.OnNewPostListerner {

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
        adapter.onResume(sharedPreferences)
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
        authorId:String,
        city:String,
        date:String,
        description:String,
        image: String
    ) {
        val newPost = Post(id,title,authorId,city,date,description,image)
        adapter.addPost(newPost)
    }

    override fun onPause() {
        super.onPause()

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        adapter.onPause(sharedPreferences)
    }

//    override fun onResume() {
//        super.onResume()
//
//    }
}