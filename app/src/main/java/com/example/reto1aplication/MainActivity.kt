package com.example.reto1aplication

import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Messenger
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reto1aplication.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var users = HashMap<String,User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        var json = sharedPreferences.getString("allUsers","NO_DATA")

        if(json != "NO_DATA"){
            Log.e("<<<",json.toString())
            val oldUsers = Gson().fromJson<HashMap<String,User>>(json.toString(),Array<Post>::class.java)

            if(!oldUsers.isEmpty()){
                users = oldUsers
            }
        }else{
            Log.e("ERROR","No se encuentra la serialziacion")
        }
        if(users.isEmpty()){
            val user1 =  User("Cpasuy06","Carolina Pasuy Pinilla", "alfa@gmail.com","aplicacionesmoviles")
            val user2 =  User("Lapsuy06","Laura Pasuy Pinilla", "beta@gmail.com","aplicacionesmoviles")
            users.put("Cpasuy06",user1);
            users.put("Lpasuy06",user2);
        }

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
            for(user in users.values){
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