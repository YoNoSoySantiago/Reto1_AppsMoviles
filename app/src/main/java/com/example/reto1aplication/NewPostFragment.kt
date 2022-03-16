package com.example.reto1aplication

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.reto1aplication.databinding.FragmentNewPostBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewPostFragment(val userLogged:String?): Fragment() {

    private var _binding: FragmentNewPostBinding?=null
    private val binding get() = _binding!!

    //STATE


    //Listerner
    var listener: OnNewPostListerner? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPostBinding.inflate(inflater,container,false)
        var view = binding.root
    
        binding.btnNewPost.setOnClickListener{

            listener?.let{
                //Aqui va la clase
                val title = binding.textTitulo.text.toString()

                val autor = this.userLogged.toString()
                val city = binding.spinnerCities.selectedItem.toString()
                val date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss")
                val description = binding.textDescription.text.toString()
                if(title.isEmpty() or autor.isEmpty() or city.isEmpty() or date.isEmpty() or description.isEmpty()){
                    Toast.makeText(activity,"Datos incompletos",Toast.LENGTH_LONG).show()
                }else{
                    it.onNewPost(title,autor,city,date,description,"Aqui se supone va una imagenxd")
                    binding.textTitulo
                }
            }
        }
        return view
    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
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
        fun newInstance(user:String?) = NewPostFragment(user)

    }
}