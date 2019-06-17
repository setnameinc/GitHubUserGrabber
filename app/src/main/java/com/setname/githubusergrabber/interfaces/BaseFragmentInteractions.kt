package com.setname.githubusergrabber.interfaces

interface BaseFragmentInteractions{

    fun showErrorMessage(message: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun initInjection()
    fun initPresenter()
}