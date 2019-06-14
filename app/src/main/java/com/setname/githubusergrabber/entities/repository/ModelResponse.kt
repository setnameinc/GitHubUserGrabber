package com.setname.githubusergrabber.entities.repository

data class ModelResponse (val total_count:Int, val items:List<User>)

data class User(val login:String, val image:String)