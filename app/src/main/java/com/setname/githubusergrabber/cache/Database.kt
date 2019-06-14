package com.setname.githubusergrabber.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.setname.githubusergrabber.entities.cache.ModelUser

@Database(entities = [ModelUser::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun databaseDao():DatabaseDAO
}