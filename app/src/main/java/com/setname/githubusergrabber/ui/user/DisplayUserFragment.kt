package com.setname.githubusergrabber.ui.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.adapters.RepositoryDisplayAdapter
import com.setname.githubusergrabber.entities.cache.Repository
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.presenter.user.DisplayUserPresenter
import kotlinx.android.synthetic.main.fragment_display_user.*
import javax.inject.Inject

class DisplayUserFragment : Fragment(), DisplayUserFragmentView {

    @Inject
    lateinit var presenter: DisplayUserPresenter

    @Inject
    lateinit var currentUser: User

    private var list: MutableList<Repository> = arrayListOf()
    private val adapter = RepositoryDisplayAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_display_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()

        val isFavouriteExist = presenter.checkIsFavouriteExists(currentUser.id)

        initUpperBlock(isFavouriteExist)
        initRecyclerView()


        loadUpperBlock()
        loadRepositoryList(isFavouriteExist)

    }

    private fun initPresenter() {
        presenter.init(this)
    }

    private fun initUpperBlock(isFavouriteExist: Boolean) {

        val selected = ContextCompat.getDrawable(context!!, R.drawable.ic_star_selected)
        val notSelected = ContextCompat.getDrawable(context!!, R.drawable.ic_star_not_selected)

        if (isFavouriteExist) {

            fragment_display_user__btn_favourite.setImageDrawable(selected)

        } else {

            fragment_display_user__btn_favourite.setImageDrawable(notSelected)

        }

        fragment_display_user__btn_favourite.setOnClickListener {

            fragment_display_user__btn_favourite.isClickable = false

            if (fragment_display_user__btn_favourite.drawable == selected) {

                presenter.removeFavourite(currentUser)
                fragment_display_user__btn_favourite.setImageDrawable(notSelected)

            } else {

                presenter.addFavourite(currentUser)
                fragment_display_user__btn_favourite.setImageDrawable(selected)

            }

            fragment_display_user__btn_favourite.isClickable = true


        }

    }

    private fun initRecyclerView() {
        fragment_display_user__rv.apply {

            adapter = this@DisplayUserFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }
    }

    private fun loadUpperBlock() {

        fun loadImage(url: String) {
            Glide.with(context!!)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(fragment_display_user__iv_user)
        }

        fun loadUsername(name: String) {
            fragment_display_user__tv_username.text = name
        }

        loadImage(currentUser.avatar_url)
        loadUsername(currentUser.login)

    }

    private fun loadRepositoryList(isFavouriteExist: Boolean) {

        if (isFavouriteExist) {

            presenter.loadRepositoryListFromCache(currentUser)

        } else {

            presenter.loadRepositoryListFromRepository(currentUser)

        }

    }

    override fun displayRepositoryList(list: List<Repository>) {
        this.list.clear()
        this.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun showErrorMessage(message: String) {

    }

}

interface DisplayUserFragmentView {
    fun displayRepositoryList(list: List<Repository>)
    fun showErrorMessage(message: String)
}