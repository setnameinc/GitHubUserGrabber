package com.setname.githubusergrabber

import android.app.Application
import com.setname.githubusergrabber.di.AppComponent
import com.setname.githubusergrabber.di.DaggerAppComponent

class App : Application() {

    companion object{

        val appComponent: AppComponent by lazy {
            DaggerAppComponent.builder().build()
        }

    }

}