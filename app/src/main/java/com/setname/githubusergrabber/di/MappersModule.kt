package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.mappers.FavouriteMapper
import com.setname.githubusergrabber.mappers.FavouriteMapperImpl
import com.setname.githubusergrabber.mappers.UserMapper
import com.setname.githubusergrabber.mappers.UserMapperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MappersModule {

    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper = UserMapperImpl()

    @Provides
    @Singleton
    fun provideFavouriteMapper(): FavouriteMapper = FavouriteMapperImpl()

}