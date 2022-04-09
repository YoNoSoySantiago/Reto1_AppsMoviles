package com.example.reto1aplication

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reto1aplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var users = HashMap<String,User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)
        val sharedPreferences = getSharedPreferences("MyPref",0)
        var json = sharedPreferences.getString("allUsers","NO_DATA")

        if(json != "NO_DATA"){
            val type: Type = object : TypeToken<HashMap<String, User>>() {}.type
            users =  Gson().fromJson(json, type)

        }
        if(users.isEmpty()){
            val user1 =  User("Cpasuy06","Carolina Pasuy Pinilla", "alfa@gmail.com","aplicacionesmoviles")
            val user2 =  User("Lapsuy06","Laura Pasuy Pinilla", "beta@gmail.com","aplicacionesmoviles")
            users[user1.id] =user1
            users[user2.id] =user2

            val json = Gson().toJson(users)
            sharedPreferences.edit().putString("allUsers",json).apply()

        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var jsonUser = sharedPreferences.getString("currentUser","NO_DATA")
        if(jsonUser != "NO_DATA") {
            val currentUser = Gson().fromJson(jsonUser,User::class.java)
            if(currentUser!=null){
                startLogIn(currentUser)
            }
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword2.text.toString()
            if(pass.isNotEmpty() and email.isNotEmpty()){
                var currentUser:User? =null
                for(user in users.values){
                    if(email.equals(user.email) and pass.equals(user.password)){
                        currentUser = user
                        break
                    }
                }
                if(currentUser != null){
                    val json = Gson().toJson(currentUser)
                    sharedPreferences.edit().putString("currentUser",json).apply()
                    startLogIn(currentUser)
                }else{
                    Toast.makeText(this.baseContext,"Datos incorrectos",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this.baseContext,"Datos incompletos",Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun startLogIn(currentUser: User) {
        val i = Intent(this, MainMenuActivity::class.java)
        i.putExtra("user", Gson().toJson(currentUser))
        startActivity(i)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}