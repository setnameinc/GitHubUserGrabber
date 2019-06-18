package com.setname.githubusergrabber.remote

import com.setname.githubusergrabber.entities.repository.ModelUserResponse
import com.setname.githubusergrabber.entities.repository.ModelRepositoryRemote
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteDao {

    @GET("search/users")
    fun getUser(@Query(value = "q") username: String): Single<ModelUserResponse>

    @GET("users/{login}/repos")
    fun getRepositories(@Path("login") login: String): Single<List<ModelRepositoryRemote>>

}