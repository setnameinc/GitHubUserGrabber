package com.setname.githubusergrabber.presenter.user

import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.domain.interactors.user.IUserInteractor
import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache
import com.setname.githubusergrabber.entities.cache.Repository
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.ui.user.DisplayUserFragmentView
import javax.inject.Inject

class UserPresenter : IUserPresenter {

    @Inject
    lateinit var userInteractor: IUserInteractor

    private lateinit var view: DisplayUserFragmentView

    override fun init(displayUserFragmentView: DisplayUserFragmentView) {

        view = displayUserFragmentView
        App.appComponent.inject(this)
        userInteractor.init(this)

    }

    override fun checkIsFavouriteExist(id: Long): Boolean {
        return userInteractor.checkIsFavouriteExist(id)
    }

    override fun addFavourite(user: User) {

        userInteractor.addFavourite(user)

    }

    override fun removeFavourite(user: User) {

        userInteractor.removeFavourite(user)

    }

    override fun loadRepositoryListFromCache(user: User) {

        userInteractor.loadRepositoryListFromCache(user)

    }

    override fun loadRepositoryListFromRemote(user: User) {

        userInteractor.loadRepositoryListFromRemote(user)

    }

    override fun displayRepositoryList(list: List<Repository>) {
        view.displayRepositoryList(list)
    }

    override fun showErrorMessage(message: String) {
        view.showErrorMessage(message)
    }

}

interface IUserPresenter {

    fun init(displayUserFragmentView: DisplayUserFragmentView)
    fun checkIsFavouriteExist(id: Long): Boolean
    fun addFavourite(user: User)
    fun removeFavourite(user: User)
    fun loadRepositoryListFromRemote(user: User)
    fun loadRepositoryListFromCache(user: User)
    fun displayRepositoryList(list: List<Repository>)
    fun showErrorMessage(message: String)
}
