package com.setname.githubusergrabber.dao

import com.setname.githubusergrabber.entities.repository.ModelUserResponse
import com.setname.githubusergrabber.entities.repository.ModelRepositoryRepository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryDao {

    @GET("search/users")
    fun getUser(@Query(value = "q") username: String): Single<ModelUserResponse>

    @GET("users/{login}/repos")
    fun getRepositories(@Path("login") login: String): Single<List<ModelRepositoryRepository>>

}