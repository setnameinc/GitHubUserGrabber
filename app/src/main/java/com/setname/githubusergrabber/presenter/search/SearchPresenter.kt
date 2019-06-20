package com.setname.githubusergrabber.presenter.search

import android.annotation.SuppressLint
import android.net.NetworkInfo
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.domain.interactors.search.ISearchInteractor
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.ui.search.ISearchDisplayFragment
import com.setname.githubusergrabber.ui.search.SearchDisplayFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SearchPresenter : MvpPresenter<SearchDisplayFragmentView>() , ISearchPresenter {

    private val disposableBag: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var iSearchInteractor: ISearchInteractor

    @Inject
    lateinit var currentUser: User

    @Inject
    lateinit var networkListener: io.reactivex.Observable<Connectivity>

    private lateinit var view: ISearchDisplayFragment

    init {

        App.appComponent.inject(this)
        iSearchInteractor.init(this)

    }

    override fun loadListOfUsers(login: String) {

        iSearchInteractor.loadListOfUsers(login)

    }

    override fun loadList(list: List<User>) {
        viewState.startShowFavouriteList(list)
        viewState.showSuccess()
    }

    override fun loadCurrentUser(user: User) {

        //doesn't use mappings, because the mapping create a new instance, but need just to update the data in the existing model

        currentUser.login = user.login
        currentUser.avatar_url = user.avatar_url
        currentUser.repos_url = user.repos_url
        currentUser.id = user.id

    }

    override fun onError(throwable: Throwable) {
        viewState.showErrorMessage(throwable.message ?: "error")
    }

    override fun loadFavouriteUsers() {
        iSearchInteractor.loadFavouriteUsers()
    }

    override fun initNetworkListener() {

        val disposableNetworkState = networkListener.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                run {

                    if (connectivity.state() == NetworkInfo.State.DISCONNECTED) {

                        viewState.showErrorView("no internet connection")

                    } else if (connectivity.state() == NetworkInfo.State.CONNECTED) {

                        viewState.hideErrorView()

                    }

                }
            }

        disposableBag.add(disposableNetworkState)

    }

    override fun clearDisposableBag() {
        iSearchInteractor.clearDisposableBag()
        disposableBag.clear()
    }

}

interface ISearchPresenter {

    fun initNetworkListener()

    fun loadListOfUsers(login: String)
    fun loadList(list: List<User>)
    fun loadCurrentUser(user: User)
    fun loadFavouriteUsers()

    fun onError(throwable: Throwable)
    fun clearDisposableBag()

}