package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.domain.interactors.search.ISearchInteractor
import com.setname.githubusergrabber.domain.interactors.search.SearchInteractor
import com.setname.githubusergrabber.domain.interactors.user.IUserInteractor
import com.setname.githubusergrabber.domain.interactors.user.UserInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorsModule {

    @Provides
    @Singleton
    fun provideSearchInteractor(): ISearchInteractor = SearchInteractor()

    @Provides
    @Singleton
    fun provideUserInteractor(): IUserInteractor = UserInteractor()

}