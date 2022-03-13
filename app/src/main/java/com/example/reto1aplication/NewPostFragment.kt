package com.example.reto1aplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reto1aplication.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {

    private var _binding: FragmentNewPostBinding?=null
    private val binding get() = _binding!!

    //STATE
    private val posts = ArrayList<String>()

    //Listerner
    var listener: OnNewPostListerner? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPostBinding.inflate(inflater,container,false)
        var view = binding.root
    
        binding.btnNewPost.setOnClickListener{
            val titulo =  binding.textTitulo.text.toString()
            val city = binding.spinnerCities.selectedItem.toString()

            listener?.let{
                //Aqui va la clase
                it.onNewPost(titulo,city,"Aqui va una Imagen")
            }
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface  OnNewPostListerner{
        fun onNewPost(post:String,city:String,image:String)
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewPostFragment()
    }
}