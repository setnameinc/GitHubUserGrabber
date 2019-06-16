package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.mappers.*
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

    @Provides
    @Singleton
    fun provideRepositoryMapperImpl():RepositoryMapper = RepositoryMapperImpl()

}