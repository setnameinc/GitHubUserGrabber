package com.setname.githubusergrabber.di

import com.setname.githubusergrabber.domain.interactors.search.SearchInteractor
import com.setname.githubusergrabber.domain.interactors.user.UserInteractor
import com.setname.githubusergrabber.presenter.search.SearchPresenter
import com.setname.githubusergrabber.presenter.user.UserPresenter
import com.setname.githubusergrabber.ui.main.MainActivity
import com.setname.githubusergrabber.ui.search.SearchDisplayFragment
import com.setname.githubusergrabber.ui.user.DisplayUserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheModule::class, RemoteModule::class, NavigatonModule::class, MappersModule::class, EntitiesModule::class, PresentersModule::class, InteractorsModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(searchDisplayFragmentImpl: SearchDisplayFragment)
    fun inject(displayUserFragmentImpl: DisplayUserFragment)

    fun inject(searchPresenter: SearchPresenter)
    fun inject(userPresenter: UserPresenter)

    fun inject(searchInteractor: SearchInteractor)
    fun inject(userInteractor: UserInteractor)

}