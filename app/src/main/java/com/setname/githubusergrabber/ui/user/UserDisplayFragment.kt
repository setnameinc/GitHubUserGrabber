package com.setname.githubusergrabber.ui.user

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.adapters.RepositoryDisplayAdapter
import com.setname.githubusergrabber.entities.cache.Repository
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.interfaces.BaseFragmentInteractions
import com.setname.githubusergrabber.interfaces.RecyclerViewInteractions
import com.setname.githubusergrabber.presenter.user.IUserPresenter
import com.setname.githubusergrabber.presenter.user.UserPresenter
import kotlinx.android.synthetic.main.fragment_display_user.*
import kotlinx.android.synthetic.main.fragment_display_user_header.*
import javax.inject.Inject

class DisplayUserFragment : Fragment(), DisplayUserFragmentView {

    private val LAYOUT = R.layout.fragment_display_user

    @Inject
    lateinit var presenter: IUserPresenter

    @Inject
    lateinit var currentUser: User

    private var list: MutableList<Repository> = arrayListOf()
    private val adapter = RepositoryDisplayAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjection()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(LAYOUT, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()

        val isFavouriteExist = presenter.checkIsFavouriteExist(currentUser.id)

        initUpperBlock(isFavouriteExist)
        initRecyclerView()

        loadUpperBlock()
        loadRepositoryList(isFavouriteExist)

    }

    override fun initInjection() {
        App.appComponent.inject(this)
    }

    override fun initPresenter() {
        presenter.init(this)
    }

    override fun initUpperBlock(isFavouriteExist: Boolean) {

        val selected = ContextCompat.getDrawable(context!!, R.drawable.ic_star_not_select_to_selected)
        val notSelected = ContextCompat.getDrawable(context!!, R.drawable.ic_star_selected_to_not_selected)

        if (isFavouriteExist) {

            fragment_display_user_header__btn_favourite.setImageDrawable(selected)

        } else {

            //drawable using by default

        }

        fragment_display_user_header__btn_favourite.setOnClickListener {

            fragment_display_user_header__btn_favourite.isClickable = false

            if (fragment_display_user_header__btn_favourite.drawable == selected) {

                presenter.removeFavourite(currentUser)
                fragment_display_user_header__btn_favourite.setImageDrawable(notSelected)

            } else {

                presenter.addFavourite(currentUser)
                fragment_display_user_header__btn_favourite.setImageDrawable(selected)

            }

            ((fragment_display_user_header__btn_favourite.drawable) as AnimatedVectorDrawable).start()

            fragment_display_user_header__btn_favourite.isClickable = true

        }

    }

    override fun initRecyclerView() {
        fragment_display_user__rv.apply {

            adapter = this@DisplayUserFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }
    }

    override fun loadUpperBlock() {

        fun loadImage(url: String) {
            Glide.with(context!!)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(fragment_display_user_header__iv_user)
        }

        fun loadUsername(name: String) {
            fragment_display_user_header__tv_username.text = name
        }

        loadImage(currentUser.avatar_url)
        loadUsername(currentUser.login)

    }

    override fun loadRepositoryList(isFavouriteExist: Boolean) {

        if (isFavouriteExist) {

            presenter.loadRepositoryListFromCache(currentUser)

        } else {

            presenter.loadRepositoryListFromRemote(currentUser)

        }

    }

    override fun displayRepositoryList(list: List<Repository>) {
        this.list.clear()
        this.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun showErrorMessage(message: String) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        //Or later init

    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

    }

}

interface DisplayUserFragmentView : BaseFragmentInteractions, RecyclerViewInteractions, RepositoryListInteractions {

    fun initUpperBlock(isFavouriteExist: Boolean)
    fun loadUpperBlock()
    fun loadRepositoryList(isFavouriteExist: Boolean)

}

interface RepositoryListInteractions{

    fun displayRepositoryList(list: List<Repository>)

}