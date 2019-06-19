package com.setname.githubusergrabber.presenter.search

import android.annotation.SuppressLint
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.domain.interactors.search.ISearchInteractor
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.ui.search.SearchDisplayFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter : ISearchPresenter {

    @Inject
    lateinit var iSearchInteractor: ISearchInteractor

    @Inject
    lateinit var currentUser: User

    @Inject
    lateinit var networkListener: io.reactivex.Observable<Connectivity>

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

    @SuppressLint("CheckResult")
    override fun initNetworkListener() {

        networkListener.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                run {

                    if (connectivity.state() == NetworkInfo.State.DISCONNECTED) {

                        view.showErrorView("no internet connection")

                    } else if (connectivity.state() == NetworkInfo.State.CONNECTED) {

                        view.hideErrorView()

                    }

                }
            }

    }

}

interface ISearchPresenter {

    fun init(view: SearchDisplayFragmentView)
    fun initNetworkListener()

    fun loadListOfUsers(login: String)
    fun loadList(list: List<User>)
    fun loadCurrentUser(user: User)
    fun loadFavouriteUsers()

    fun onError(throwable: Throwable)

}