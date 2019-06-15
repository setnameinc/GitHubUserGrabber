package com.setname.githubusergrabber.ui.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.presenter.user.DisplayUserPresenter
import kotlinx.android.synthetic.main.fragment_display_user.*
import javax.inject.Inject

class DisplayUserFragmentImpl : Fragment(), DisplayUserFragment {

    @Inject
    lateinit var presenter: DisplayUserPresenter

    @Inject
    lateinit var currentUser: User

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

        initUpperBlock()

    }

    private fun initPresenter() {
        presenter.init(this)
    }

    private fun initUpperBlock() {

        val selected = ContextCompat.getDrawable(context!!, R.drawable.ic_star_selected)
        val notSelected = ContextCompat.getDrawable(context!!, R.drawable.ic_star_not_selected)

        fun loadImage(url: String) {
            Glide.with(context!!)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(fragment_display_user__iv_user)
        }

        fun loadUsername(name: String) {
            fragment_display_user__tv_username.text = name
        }

        fun initButtonFavouriteListener() {

            fragment_display_user__btn_favourite.setOnClickListener {

                fragment_display_user__btn_favourite.isClickable = false

                if (fragment_display_user__btn_favourite.drawable == selected) {

                    presenter.deleteFavourite(currentUser.id)
                    fragment_display_user__btn_favourite.setImageDrawable(notSelected)

                } else {

                    presenter.insertFavourite(currentUser)
                    fragment_display_user__btn_favourite.setImageDrawable(selected)

                }

                fragment_display_user__btn_favourite.isClickable = true


            }

        }

        fun initButton() {

            if (presenter.checkIsFavouriteExists(currentUser.id)) {

                //is favourite
                fragment_display_user__btn_favourite.setImageDrawable(selected)

            } else {

                fragment_display_user__btn_favourite.setImageDrawable(notSelected)

            }

            initButtonFavouriteListener()

        }

        loadImage(currentUser.avatar_url)
        loadUsername(currentUser.login)
        initButton()


    }

    override fun displayUserInfo() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayRepositoryList() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

interface DisplayUserFragment {
    fun displayUserInfo()
    fun displayRepositoryList()
}