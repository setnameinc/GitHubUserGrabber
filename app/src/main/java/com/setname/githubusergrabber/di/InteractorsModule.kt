package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.domain.search.SearchInteractor
import com.setname.githubusergrabber.domain.search.SearchInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorsModule {

    @Provides
    @Singleton
    fun provideSearchInteractor(): SearchInteractor = SearchInteractorImpl()

}