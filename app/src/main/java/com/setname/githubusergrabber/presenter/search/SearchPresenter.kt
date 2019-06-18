package com.setname.githubusergrabber.presenter.search

import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.domain.interactors.search.ISearchInteractor
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.ui.search.SearchDisplayFragmentView
import javax.inject.Inject

class SearchPresenter : ISearchPresenter {

    @Inject
    lateinit var iSearchInteractor: ISearchInteractor

    @Inject
    lateinit var currentUser: User

    private lateinit var view: SearchDisplayFragmentView

    override fun init(view: SearchDisplayFragmentView) {

        this.view = view
        App.appComponent.inject(this)
        iSearchInteractor.init(this)

    }

    override fun loadListOfUsers(login: String) {

        iSearchInteractor.loadListOfUsers(login)

    }

    override fun loadList(list: List<User>) {
        view.loadListOfUsers(list)
        view.hideProgressBar()
    }

    override fun loadCurrentUser(user: User) {

        //doesn't use mappings, because the mapping create a new instance, but need just to update the data in the existing model

        currentUser.login = user.login
        currentUser.avatar_url = user.avatar_url
        currentUser.repos_url = user.repos_url
        currentUser.id = user.id

    }

    override fun onError(throwable: Throwable) {
        view.showErrorMessage(throwable.message ?: "error")
    }

    override fun loadFavouriteUsers() {
        iSearchInteractor.loadFavouriteUsers()
    }

}

interface ISearchPresenter {

    fun init(view: SearchDisplayFragmentView)
    fun loadListOfUsers(login: String)
    fun loadList(list: List<User>)
    fun loadCurrentUser(user: User)
    fun loadFavouriteUsers()

    fun onError(throwable: Throwable)

}