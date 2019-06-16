package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache
import com.setname.githubusergrabber.entities.repository.User
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EntitiesModule {

    @Provides
    @Singleton
    fun provideUserList(): MutableList<User> = arrayListOf()

    @Provides
    @Singleton
    fun provideCurrentOpenedUser(): User = User()

}