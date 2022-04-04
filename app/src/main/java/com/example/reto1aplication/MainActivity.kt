package com.example.reto1aplication

import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Messenger
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reto1aplication.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var users = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {

        val user1 =  User("Cpasuy06","Carolina Pasuy Pinilla", "alfa@gmail.com","aplicacionesmoviles")
        val user2 =  User("Lapsuy06","Laura Pasuy Pinilla", "beta@gmail.com","aplicacionesmoviles")
        users.add(user1)
        users.add(user2)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener() {
            val i = Intent(this, MainMenuActivity::class.java)
            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword2.text.toString()
            var currentUser:User? =null
            var username =""
            for(user in users){
                if(email.equals(user.email) and pass.equals(user.password)){
                    currentUser = user
                    username=user.user
                    break
                }
            }
            if(currentUser != null){
                i.putExtra("user", Gson().toJson(currentUser))
                startActivity(i)
            }else{
                Toast.makeText(this.baseContext,"Datos incorrectos",Toast.LENGTH_LONG).show()
            }

        }
    }
}