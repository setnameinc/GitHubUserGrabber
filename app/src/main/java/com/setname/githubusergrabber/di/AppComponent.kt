package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.ui.main.MainActivity
import com.setname.githubusergrabber.ui.search.SearchDisplayFragment
import com.setname.githubusergrabber.ui.user.DisplayUserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheModule::class, RemoteModule::class, NavigatonModule::class, MappersModule::class, EntitiesModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(searchDisplayFragmentImpl: SearchDisplayFragment)
    fun inject(displayUserFragmentImpl: DisplayUserFragment)

}