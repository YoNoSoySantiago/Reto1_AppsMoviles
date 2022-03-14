package com.example.reto1aplication

import java.util.*

class User {
    var id:String
    var name:String

    constructor(name:String){
        this.id = UUID.randomUUID().toString()
        this.name = name
    }
}