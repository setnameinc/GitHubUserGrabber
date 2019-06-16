package com.setname.githubusergrabber.mappers

import com.setname.githubusergrabber.entities.cache.ModelFavourite
import com.setname.githubusergrabber.entities.repository.ModelUserResponse
import com.setname.githubusergrabber.entities.repository.User


class UserMapperImpl : UserMapper {

    override fun convertTo(modelUserResponse: ModelUserResponse): List<User> = modelUserResponse.items

    override fun convertFrom(modelUserDatabase: ModelFavourite): User = User(
        modelUserDatabase.user.login,
        modelUserDatabase.user.avatar_url,
        modelUserDatabase.id,
        modelUserDatabase.user.repos_url
    )

}

interface UserMapper {

    fun convertTo(modelUserResponse: ModelUserResponse): List<User>

    fun convertFrom(modelUserDatabase: ModelFavourite): User


}