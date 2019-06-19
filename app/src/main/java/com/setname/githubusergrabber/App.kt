package com.setname.githubusergrabber

import android.app.Application
import com.setname.githubusergrabber.di.AppComponent
import com.setname.githubusergrabber.di.CacheModule
import com.setname.githubusergrabber.di.DaggerAppComponent
import com.setname.githubusergrabber.di.RemoteModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .cacheModule(CacheModule(applicationContext))
            .remoteModule(RemoteModule(applicationContext))
            .build()

    }

    companion object{

        lateinit var appComponent: AppComponent

    }

}