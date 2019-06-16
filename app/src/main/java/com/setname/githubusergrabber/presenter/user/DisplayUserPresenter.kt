package com.setname.githubusergrabber.presenter.user

import com.setname.githubusergrabber.dao.DatabaseDao
import com.setname.githubusergrabber.dao.RepositoryDao
import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache
import com.setname.githubusergrabber.entities.repository.ModelRepositoryRepository
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.mappers.FavouriteMapper
import com.setname.githubusergrabber.mappers.RepositoryMapper
import com.setname.githubusergrabber.ui.user.DisplayUserFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DisplayUserPresenter @Inject constructor() {

    @Inject
    lateinit var retrofitDao: RepositoryDao

    @Inject
    lateinit var databaseDao: DatabaseDao

    @Inject
    lateinit var favouriteMapper: FavouriteMapper

    @Inject
    lateinit var repositoryMapper: RepositoryMapper

    private lateinit var view: DisplayUserFragmentView

    var listOfRepositoryRepositories: MutableList<ModelRepositoryCache> = arrayListOf()

    fun init(displayUserFragmentView: DisplayUserFragmentView) {
        view = displayUserFragmentView
    }

    fun checkIsFavouriteExists(id: Long): Boolean {
        return databaseDao.isExistFavourite(id) == 1L
    }

    fun addFavourite(user: User) {
        insertFavourite(user)

    }

    fun removeFavourite(user: User) {
        deleteFavourite(user.id)
    }

    private fun insertFavourite(user: User) {

        databaseDao.insertFavourite(favouriteMapper.convertFrom(user))
        listOfRepositoryRepositories.forEach { databaseDao.insertRepository(it) }

    }

    private fun deleteFavourite(id: Long) {

        databaseDao.deleteFavourite(id)
        databaseDao.deleteRepository(id)

    }

    private fun onError(throwable: Throwable) {

        view.showErrorMessage(throwable.message!!)

    }

    private fun onModelResponseFetched(list: List<ModelRepositoryRepository>) {

        listOfRepositoryRepositories.clear()
        listOfRepositoryRepositories.addAll(repositoryMapper.convertToCacheListFromRepositoryList(list))

        view.displayRepositoryList(repositoryMapper.convertToRepositoryListFromRepository(list))

    }

    fun loadRepositoryListFromCache(user: User) {

        val list = databaseDao.getAllRepositories(user.id)

        view.displayRepositoryList(repositoryMapper.convertToRepositoryListFromCache(list))

    }

    fun loadRepositoryListFromRepository(user: User) {

        val dispatcher =
            retrofitDao.getRepositories(user.login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onModelResponseFetched, this::onError)

    }

}