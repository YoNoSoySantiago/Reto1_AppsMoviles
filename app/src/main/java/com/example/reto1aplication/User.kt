package com.example.reto1aplication

import java.util.*

class User {
    var id:String
    var user:String
    var name:String
    var email: String
    var password:String

    //Falta Encriptar
    constructor(user:String,name:String,email:String,password:String){
        this.user = user
        this.id = UUID.randomUUID().toString()
        this.name = name
        this.email = email
        this.password = password
    }
}