package com.setname.githubusergrabber.entities.repository

data class ModelResponse(val total_count: Int, val items: List<User>)

data class User(var login: String = "", var avatar_url: String = "", var id: Long = -1, var repos_url: String = "")