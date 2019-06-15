package com.setname.githubusergrabber.presenter.user

import com.setname.githubusergrabber.dao.DatabaseDao
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.mappers.FavouriteMapper
import com.setname.githubusergrabber.ui.user.DisplayUserFragment
import javax.inject.Inject

class DisplayUserPresenter @Inject constructor() {

    @Inject
    lateinit var databaseDao: DatabaseDao

    @Inject
    lateinit var favouriteMapper: FavouriteMapper

    private lateinit var view: DisplayUserFragment

    fun init(displayUserFragment: DisplayUserFragment) {
        view = displayUserFragment
    }

    fun insertFavourite(user: User) {

        databaseDao.insertFavourite(favouriteMapper.convertFrom(user))

    }

    fun checkIsFavouriteExists(id: Long): Boolean = databaseDao.isExist(id) == 1L

    fun deleteFavourite(id: Long) {
        databaseDao.deleteFavourite(id)
    }

}