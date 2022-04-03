package com.example.reto1aplication

import java.io.File
import java.util.*

class Post {
    var id:String
    var title:String
    var autor: String
    var city:String
    var description:String
    var date: String
    var image:File?

    constructor (id:String, title:String, author:String, city:String, date:String, description:String, image: File?){

        this.id = id
        this.title = title
        this.autor = author
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