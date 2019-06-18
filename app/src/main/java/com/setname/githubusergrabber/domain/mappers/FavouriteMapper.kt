package com.setname.githubusergrabber.domain.mappers

import com.setname.githubusergrabber.entities.cache.ModelFavourite
import com.setname.githubusergrabber.entities.cache.ModelUserDatabase
import com.setname.githubusergrabber.entities.repository.User

class FavouriteMapperImpl : FavouriteMapper {

    override fun convertTo(modelFavourite: ModelFavourite) = User(
        modelFavourite.user.login,
        modelFavourite.user.avatar_url,
        modelFavourite.id,
        modelFavourite.user.repos_url
    )

    override fun convertFrom(user: User): ModelFavourite =
        ModelFavourite(user.id, ModelUserDatabase(user.login, user.avatar_url, user.repos_url))

}

interface FavouriteMapper {
    fun convertTo(modelFavourite: ModelFavourite): User
    fun convertFrom(user: User): ModelFavourite
}