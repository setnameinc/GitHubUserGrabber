package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.presenter.search.SearchDisplayPresenter
import com.setname.githubusergrabber.presenter.search.SearchDisplayPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule{

    @Provides
    @Singleton
    fun provideSearchDisplayModule(): SearchDisplayPresenter = SearchDisplayPresenterImpl()

}