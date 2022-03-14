package com.example.reto1aplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
//            val title =  binding.textTitulo.text.toString()
//            val city = binding.spinnerCities.selectedItem.toString()
            Toast.makeText(activity,"is working",Toast.LENGTH_LONG).show()
            listener?.let{
                //Aqui va la clase

               it.onNewPost("title","Cpasuy06","Cali","06/05/2001","Inserte una descripcion inspiradora","Aqui se supone va una imagenxd")
            }
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface  OnNewPostListerner{
        fun onNewPost(title:String,autor:String,city:String,date:String,description:String,image:String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewPostFragment()
    }
}