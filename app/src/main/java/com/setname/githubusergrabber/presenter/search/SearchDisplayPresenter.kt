package com.setname.githubusergrabber.presenter.search

import android.util.Log
import com.setname.githubusergrabber.entities.repository.ModelResponse
import com.setname.githubusergrabber.services.RepositoryService
import com.setname.githubusergrabber.ui.search.SearchDisplayFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchDisplayPresenter(val view: SearchDisplayFragment) {

    @Inject
    lateinit var retrofitService: RepositoryService

    fun getUserListByLogin(login: String) {

        val dispatcher =
            retrofitService.getPage(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onModelResponseFetched, this::onError)

    }

    private fun onModelResponseFetched(modelResponse: ModelResponse) {
        Log.i("MainTest", "count = ${modelResponse.items[0].login}")
    }

    private fun onError(throwable: Throwable) {
        Log.d("MainTest", throwable.message)
    }

}