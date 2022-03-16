package com.example.reto1aplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reto1aplication.databinding.FragmentNewProfileBinding

class NewProfileFragment (): Fragment() {
    private var _binding: FragmentNewProfileBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewProfileBinding.inflate(inflater,container,false)
        val view = binding.root



        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewProfileFragment()
    }
}