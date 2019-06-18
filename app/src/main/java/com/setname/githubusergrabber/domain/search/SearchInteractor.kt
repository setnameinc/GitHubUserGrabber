package com.setname.githubusergrabber.domain.search

import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.dao.DatabaseDao
import com.setname.githubusergrabber.dao.RepositoryDao
import com.setname.githubusergrabber.entities.repository.ModelUserResponse
import com.setname.githubusergrabber.mappers.FavouriteMapper
import com.setname.githubusergrabber.mappers.UserMapper
import com.setname.githubusergrabber.presenter.search.SearchDisplayPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchInteractorImpl : SearchInteractor{

    @Inject
    lateinit var retrofitDao: RepositoryDao

    @Inject
    lateinit var userMapper: UserMapper

    @Inject
    lateinit var databaseDao: DatabaseDao

    @Inject
    lateinit var favouriteMapper: FavouriteMapper

    private lateinit var searchDisplayPresenter: SearchDisplayPresenter

    override fun loadListOfUsers(login:String) {
        val dispatcher =
            retrofitDao.getUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onModelResponseFetched, this::onError)
    }

    override fun init(searchDisplayPresenter: SearchDisplayPresenter) {
        App.appComponent.inject(this)
        this.searchDisplayPresenter = searchDisplayPresenter
    }

    override fun loadFavouriteUsers() {

        searchDisplayPresenter.loadList(databaseDao.getAllFavourites().map { favouriteMapper.convertTo(it) })

    }

    private fun onModelResponseFetched(modelUserResponse: ModelUserResponse) {

        searchDisplayPresenter.loadList(userMapper.convertTo(modelUserResponse))

    }

    private fun onError(throwable: Throwable) {

        searchDisplayPresenter.onError(throwable)

    }

}

interface SearchInteractor {

    fun init(searchDisplayPresenter: SearchDisplayPresenter)
    fun loadListOfUsers(login:String)
    fun loadFavouriteUsers()

}