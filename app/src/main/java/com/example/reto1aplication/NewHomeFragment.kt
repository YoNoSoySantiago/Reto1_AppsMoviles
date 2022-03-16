package com.example.reto1aplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reto1aplication.databinding.FragmentNewHomeBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [NewHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewHomeFragment : Fragment(),NewPostFragment.OnNewPostListerner {

    private var _binding: FragmentNewHomeBinding?=null
    private val binding get() =_binding!!


    //STATE
    private val adapter = PostsAdapter()


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

    override fun onNewPost(title:String,autor:String,city:String,date:String,description:String,image:String) {
        val newPost = Post(title,autor,city,date,description,image)
        adapter.addPost(newPost)
    }
}