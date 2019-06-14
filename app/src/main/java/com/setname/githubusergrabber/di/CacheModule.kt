package com.setname.githubusergrabber.di

import android.arch.persistence.room.Room
import android.content.Context
import com.setname.githubusergrabber.cache.Database
import com.setname.githubusergrabber.cache.DatabaseDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideRoom(context: Context) = Room.databaseBuilder(
        context,
        Database::class.java,
        "Database.db"
    ).allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideDatabaseDAO(database: Database) = database.databaseDao()

}