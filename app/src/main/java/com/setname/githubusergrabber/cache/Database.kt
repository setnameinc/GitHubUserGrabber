package com.setname.githubusergrabber.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.setname.githubusergrabber.dao.DatabaseDao
import com.setname.githubusergrabber.databaseconverters.UserConverter
import com.setname.githubusergrabber.entities.cache.ModelFavourite
import javax.inject.Inject

@Database(entities = [ModelFavourite::class], version = 2)
@TypeConverters(UserConverter::class)

abstract class Database : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}