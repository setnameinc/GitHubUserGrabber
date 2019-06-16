package com.setname.githubusergrabber.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.setname.githubusergrabber.dao.DatabaseDao
import com.setname.githubusergrabber.databaseconverters.RepositoryConverter
import com.setname.githubusergrabber.databaseconverters.UserConverter
import com.setname.githubusergrabber.entities.cache.ModelFavourite
import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache

@Database(entities = [ModelFavourite::class, ModelRepositoryCache::class], version = 1)
@TypeConverters(UserConverter::class, RepositoryConverter::class)

abstract class Database : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}