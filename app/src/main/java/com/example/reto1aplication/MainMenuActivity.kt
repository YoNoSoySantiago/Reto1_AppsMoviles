package com.example.reto1aplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Messenger
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.reto1aplication.databinding.ActivityMainMenuBinding
import com.google.gson.Gson
import java.security.AccessController.getContext


class MainMenuActivity : AppCompatActivity() {
    private lateinit var newHomeFragment: NewHomeFragment
    private lateinit var newPostFragment: NewPostFragment
    private lateinit var newProfileFragment: NewProfileFragment

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val userLogged = intent.getStringExtra("user")
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var user = Gson().fromJson(userLogged, User::class.java)
        newHomeFragment = NewHomeFragment.newInstance()
        newPostFragment = NewPostFragment.newInstance(user)
        newProfileFragment = NewProfileFragment.newInstance(user)

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}