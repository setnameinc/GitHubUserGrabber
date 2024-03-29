package com.setname.githubusergrabber.entities.repository

data class ModelRepositoryRemote(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    val owner: Owner
)

data class Owner(
    val id: Long
)