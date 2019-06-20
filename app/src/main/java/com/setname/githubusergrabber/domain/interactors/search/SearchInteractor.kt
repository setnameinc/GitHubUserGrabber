package com.setname.githubusergrabber.domain.interactors.search

import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.cache.DatabaseDao
import com.setname.githubusergrabber.domain.mappers.FavouriteMapper
import com.setname.githubusergrabber.domain.mappers.UserMapper
import com.setname.githubusergrabber.entities.repository.ModelUserResponse
import com.setname.githubusergrabber.presenter.search.ISearchPresenter
import com.setname.githubusergrabber.remote.RemoteDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchInteractor : ISearchInteractor {

    private val disposableBag: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var retrofitDao: RemoteDao

    @Inject
    lateinit var userMapper: UserMapper

    @Inject
    lateinit var databaseDao: DatabaseDao

    @Inject
    lateinit var favouriteMapper: FavouriteMapper

    private lateinit var ISearchPresenter: ISearchPresenter

    override fun loadListOfUsers(login: String) {
        val dispatcher =
            retrofitDao.getUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onModelResponseFetched, this::onError)
    }

    override fun init(ISearchPresenter: ISearchPresenter) {
        App.appComponent.inject(this)
        this.ISearchPresenter = ISearchPresenter
    }

    override fun loadFavouriteUsers() {

        ISearchPresenter.loadList(databaseDao.getAllFavourites().map { favouriteMapper.convertTo(it) })

    }

    override fun onModelResponseFetched(modelUserResponse: ModelUserResponse) {

        ISearchPresenter.loadList(userMapper.convertTo(modelUserResponse))

    }

    override fun onError(throwable: Throwable) {

        ISearchPresenter.onError(throwable)

    }

    override fun clearDisposableBag() {

        disposableBag.clear()

    }

}

interface ISearchInteractor : SearchRemoteResponse {

    fun init(ISearchPresenter: ISearchPresenter)

    fun loadListOfUsers(login: String)

    fun loadFavouriteUsers()

    fun clearDisposableBag()

}

interface SearchRemoteResponse {

    fun onModelResponseFetched(modelUserResponse: ModelUserResponse)
    fun onError(throwable: Throwable)

}