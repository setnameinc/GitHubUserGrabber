package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.ui.main.MainActivity
import com.setname.githubusergrabber.ui.search.SearchDisplayFragmentImpl
import com.setname.githubusergrabber.ui.user.DisplayUserFragmentImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheModule::class, RemoteModule::class, NavigatonModule::class, MappersModule::class, EntitiesModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(searchDisplayFragmentImpl: SearchDisplayFragmentImpl)
    fun inject(displayUserFragmentImpl: DisplayUserFragmentImpl)

}