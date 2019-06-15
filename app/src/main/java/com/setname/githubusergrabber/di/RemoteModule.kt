package com.setname.githubusergrabber.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.setname.githubusergrabber.contants.RemoteModuleUtils
import com.setname.githubusergrabber.dao.RepositoryDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteModuleUtils.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideGeneralServices(retrofit: Retrofit): RepositoryDao = retrofit.create(RepositoryDao::class.java)
}