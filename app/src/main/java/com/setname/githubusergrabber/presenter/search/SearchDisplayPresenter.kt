package com.setname.githubusergrabber.presenter.search

import com.setname.githubusergrabber.dao.DatabaseDao
import com.setname.githubusergrabber.dao.RepositoryDao
import com.setname.githubusergrabber.entities.repository.ModelUserResponse
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.mappers.FavouriteMapper
import com.setname.githubusergrabber.mappers.UserMapper
import com.setname.githubusergrabber.ui.search.SearchDisplayFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchDisplayPresenter @Inject constructor() {

    @Inject
    lateinit var retrofitDao: RepositoryDao

    @Inject
    lateinit var userMapper: UserMapper

    @Inject
    lateinit var currentUser: User

    @Inject
    lateinit var databaseDao: DatabaseDao

    @Inject
    lateinit var favouriteMapper: FavouriteMapper

    private lateinit var view: SearchDisplayFragmentView

    fun init(view: SearchDisplayFragmentView) {
        this.view = view
    }

    fun loadListOfUsersByLogin(login: String) {

        val dispatcher =
            retrofitDao.getUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onModelResponseFetched, this::onError)

    }

    private fun onModelResponseFetched(modelUserResponse: ModelUserResponse) {

        view.loadListOfUsers(userMapper.convertTo(modelUserResponse))
        view.hideProgressBar()

    }

    private fun onError(throwable: Throwable) {

        view.showErrorMessage(throwable.message!!)
        view.hideProgressBar()

    }

    fun loadFavouriteUsers() {

        view.loadListOfUsers(databaseDao.getAllFavourites().map { favouriteMapper.convertTo(it) })
        view.hideProgressBar()

    }

    fun loadCurrentUser(user: User) {

        currentUser.login = user.login
        currentUser.avatar_url = user.avatar_url
        currentUser.repos_url = user.repos_url
        currentUser.id = user.id

    }

}