package com.setname.githubusergrabber.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.setname.githubusergrabber.cache.DatabaseDao
import com.setname.githubusergrabber.cache.databaseconverters.RepositoryConverter
import com.setname.githubusergrabber.cache.databaseconverters.UserConverter
import com.setname.githubusergrabber.entities.cache.ModelFavourite
import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache

@Database(entities = [ModelFavourite::class, ModelRepositoryCache::class], version = 1)
@TypeConverters(UserConverter::class, RepositoryConverter::class)

abstract class Database : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}