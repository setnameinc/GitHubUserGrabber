package com.setname.githubusergrabber.services

import com.setname.githubusergrabber.entities.repository.ModelResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryService {

    @GET("search/users")
    fun getPage(@Query(value = "q") username: String): Single<ModelResponse>

}