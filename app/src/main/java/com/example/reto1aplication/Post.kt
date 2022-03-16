package com.example.reto1aplication

import java.util.*

class Post {
    var id:String
    var title:String
    var autor: String
    var city:String
    var description:String
    var date: String
    var image:String

    constructor (title:String,autor:String,city:String,date:String,description:String,image:String){
        this.id = UUID.randomUUID().toString()
        this.title = title
        this.autor = autor
        this.city = city
        this.image = image
        this.description = description
        this.date = date
    }

//    constructor(){
//        this.id = UUID.randomUUID().toString()
//        this.title = "Default Title"
//        this.autor = "Pues Yo"
//        this.city = "Cali"
//        this.image = "Here IMAGE"
//        this.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
//                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
//                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
//                "nisi ut aliquip ex ea commodo consequat."
//        this.date = "06/05/2001"
//    }
}