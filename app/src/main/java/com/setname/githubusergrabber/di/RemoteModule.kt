package com.setname.githubusergrabber.di

import android.annotation.SuppressLint
import android.content.Context
import android.database.Observable
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.setname.githubusergrabber.constants.RemoteModuleUtils
import com.setname.githubusergrabber.remote.RemoteDao
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class RemoteModule(private val context: Context) {

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
    fun provideGeneralServices(retrofit: Retrofit): RemoteDao = retrofit.create(RemoteDao::class.java)

    @Provides
    @Singleton
    fun provideNetworkListener(context: Context):io.reactivex.Observable<Connectivity> =
        ReactiveNetwork.observeNetworkConnectivity(context?.applicationContext)


}