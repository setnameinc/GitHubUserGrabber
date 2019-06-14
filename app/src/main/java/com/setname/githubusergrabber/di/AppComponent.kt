package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheModule::class, RemoteModule::class, NavigatonModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}