package com.setname.githubusergrabber.domain.interactors.user

import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.cache.DatabaseDao
import com.setname.githubusergrabber.domain.mappers.FavouriteMapper
import com.setname.githubusergrabber.domain.mappers.RepositoryMapper
import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache
import com.setname.githubusergrabber.entities.repository.ModelRepositoryRemote
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.presenter.user.IUserPresenter
import com.setname.githubusergrabber.remote.RemoteDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserInteractor : IUserInteractor {

    @Inject
    lateinit var retrofitDao: RemoteDao

    @Inject
    lateinit var databaseDao: DatabaseDao

    @Inject
    lateinit var favouriteMapper: FavouriteMapper

    @Inject
    lateinit var repositoryMapper: RepositoryMapper

    private lateinit var iSearchPresenter: IUserPresenter

    private var listOfRepositoryRepositories: MutableList<ModelRepositoryCache> = arrayListOf()

    override fun init(iSearchPresenter: IUserPresenter) {
        App.appComponent.inject(this)
        this.iSearchPresenter = iSearchPresenter
    }

    override fun checkIsFavouriteExist(id: Long): Boolean {
        return databaseDao.isExistFavourite(id) == 1L
    }

    override fun addFavourite(user: User) {

        databaseDao.insertFavourite(favouriteMapper.convertFrom(user))
        listOfRepositoryRepositories.forEach { databaseDao.insertRepository(it) }

    }

    override fun removeFavourite(user: User) {
        databaseDao.deleteFavourite(user.id)
        databaseDao.deleteRepository(user.id)
    }

    override fun loadRepositoryListFromCache(user: User) {

        val list = databaseDao.getAllRepositories(user.id)

        iSearchPresenter.displayRepositoryList(repositoryMapper.convertToRepositoryListFromCache(list))

    }

    override fun loadRepositoryListFromRemote(user: User) {

        val dispatcher =
            retrofitDao.getRepositories(user.login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onModelResponseFetched, this::onError)

    }

    override fun onError(throwable: Throwable) {

        iSearchPresenter.showErrorMessage(throwable.message!!)

    }

    override fun onModelResponseFetched(list: List<ModelRepositoryRemote>) {

        listOfRepositoryRepositories.clear()
        listOfRepositoryRepositories.addAll(repositoryMapper.convertToCacheListFromRepositoryList(list))

        iSearchPresenter.displayRepositoryList(repositoryMapper.convertToRepositoryListFromRepository(list))

    }

}

interface IUserInteractor : UserRemoteResponce {

    fun init(iSearchPresenter: IUserPresenter)
    fun checkIsFavouriteExist(id: Long): Boolean
    fun addFavourite(user: User)
    fun removeFavourite(user: User)
    fun loadRepositoryListFromRemote(user: User)
    fun loadRepositoryListFromCache(user: User)

}

interface UserRemoteResponce{
    fun onModelResponseFetched(list: List<ModelRepositoryRemote>)
    fun onError(throwable: Throwable)
}