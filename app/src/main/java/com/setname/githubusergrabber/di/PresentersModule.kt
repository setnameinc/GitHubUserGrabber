package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.presenter.search.ISearchPresenter
import com.setname.githubusergrabber.presenter.search.SearchPresenter
import com.setname.githubusergrabber.presenter.user.IUserPresenter
import com.setname.githubusergrabber.presenter.user.UserPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule {

    @Provides
    @Singleton
    fun provideSearchDisplayModule(): ISearchPresenter = SearchPresenter()

    @Provides
    @Singleton
    fun provideUserInteractor(): IUserPresenter = UserPresenter()

}