package com.example.reto1aplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.reto1aplication.databinding.ActivityMainBinding
import com.example.reto1aplication.databinding.ActivityMainMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenuActivity : AppCompatActivity() {


    private lateinit var newHomeFragment: NewHomeFragment
    private lateinit var newPostFragment: NewPostFragment
    private lateinit var newProfileFragment: NewProfileFragment

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        newHomeFragment = NewHomeFragment.newInstance()
        newPostFragment = NewPostFragment.newInstance()
        newProfileFragment = NewProfileFragment.newInstance()

        newPostFragment.listener = newHomeFragment
        showFragment(newHomeFragment)



        binding.navigator.setOnItemSelectedListener { menuItem->
            if(menuItem.itemId == R.id.homeItem){
                showFragment(newHomeFragment)
            }else if(menuItem.itemId == R.id.postItem){
                showFragment(newPostFragment)
            }else if (menuItem.itemId == R.id.profileItem){
                showFragment(newProfileFragment)
            }
            true
        }
    }

    fun showFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment)
        transaction.commit()
    }
}