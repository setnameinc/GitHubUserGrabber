package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.contants.Navigation
import com.setname.githubusergrabber.ui.search.SearchDisplayFragment
import com.setname.githubusergrabber.ui.search.SearchDisplayFragmentImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import javax.inject.Named
import javax.inject.Singleton

@Module
class NavigatonModule {

    private val firstLevelNavigation = Cicerone.create()

    @Provides
    @Singleton
    @Named(Navigation.ROUTER_FIRST_LEVEL)
    fun provideFirstLevelRouter() = firstLevelNavigation.router

    @Provides
    @Singleton
    @Named(Navigation.NAVIGATION_HOLDER_FIRST_LEVEL)
    fun provideFirstLevelNavigationHolder() = firstLevelNavigation.navigatorHolder

}