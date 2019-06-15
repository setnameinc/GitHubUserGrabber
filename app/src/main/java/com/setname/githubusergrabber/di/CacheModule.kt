package com.setname.githubusergrabber.di

import android.arch.persistence.room.Room
import android.content.Context
import com.setname.githubusergrabber.cache.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database = Room.databaseBuilder(
        context,
        Database::class.java,
        "Database.db"
    ).allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideDatabaseDAO(database: Database) = database.databaseDao()

}