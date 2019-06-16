package com.setname.githubusergrabber.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.transition.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.adapters.SearchDisplayAdapter
import com.setname.githubusergrabber.contants.Navigation
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.navigation.Screens
import com.setname.githubusergrabber.presenter.search.SearchDisplayPresenter
import com.setname.githubusergrabber.rxutils.RxSearchObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_display_search.*
import kotlinx.android.synthetic.main.view_search_view.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class SearchDisplayFragmentImpl : Fragment(), SearchDisplayFragment {

    @Inject
    lateinit var presenter: SearchDisplayPresenter

    @Inject
    @field:Named(Navigation.ROUTER_SECOND_LEVEL)
    lateinit var router: Router

    @Inject
    @field:Named(Navigation.NAV_HOLDER_SECOND_LEVEL)
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var localeView: View //temporary solution for save state
    var onCreateView = true //temporary solution for save state
    var onViewCreated = true //temporary solution for save state

    private lateinit var navigator: Navigator

    private var list: MutableList<User> = arrayListOf()
    private lateinit var adapter: SearchDisplayAdapter

    private lateinit var searchField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        initInjection()
        initNavigator()

        super.onCreate(savedInstanceState)
    }

    private fun initInjection() {
        App.appComponent.inject(this)
    }

    private fun initNavigator() {
        navigator = object : SupportAppNavigator(activity, R.id.main_container) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction?
            ) {
                fragmentTransaction?.setCustomAnimations(
                    R.anim.fragment_enter_anim,
                    R.anim.fragment_exit_anim,
                    R.anim.fragment_pop_enter_anim,
                    R.anim.fragment_pop_exit_anim
                )
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (onCreateView) {
            localeView = inflater.inflate(R.layout.fragment_display_search, container, false)

            searchField = localeView.findViewById(R.id.view_search_view__et_search)
            // the view_search_view_et_search isn't works correctly(lateinit Exception), so must use the searchField var

            onCreateView = false
        }

        return localeView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (onViewCreated) {

            initPresenter()
            initSearchListener()
            initRecyclerView()

            showFavouriteList()

            onViewCreated = false

        }

    }

    override fun loadListOfUsers(list: List<User>) {
        this.list.clear()
        this.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun showProgressBar() {
        view_search_view__pb.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        view_search_view__pb.visibility = View.INVISIBLE
    }

    override fun showErrorMessage(message: String) {

    }

    private fun initPresenter() {
        presenter.init(this)
    }

    @SuppressLint("CheckResult")
    private fun initSearchListener() {

        val rxSearchObservable = RxSearchObservable()

        rxSearchObservable
            .fromView(searchField)
            .debounce(100, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                showProgressBar()

                if (it.isNotEmpty()) {

                    setSearchIconByState(stateIsSearch = true)

                } else {

                    setSearchIconByState(stateIsSearch = false)

                }
            }

        rxSearchObservable
            .fromView(searchField)
            .debounce(1200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {

                    presenter.loadListOfUsersByLogin(searchField.text.toString())

                } else {

                    presenter.loadFavouriteUsers()

                }
            }


    }

    private fun initRecyclerView() {

        adapter = SearchDisplayAdapter(list,
            object : AdapterDisplaySearchClickListener {

                override fun navigateToFullUserInformation(pos: Int) {

                    Log.i("MainTest", "clicked")

                    presenter.loadCurrentUser(list[pos])
                    router.navigateTo(Screens.DisplayUserFragmentScreen())
                }

            })

        fragment_display_search_rv.apply {

            adapter = this@SearchDisplayFragmentImpl.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }

    }

    private fun setSearchIconByState(stateIsSearch: Boolean) {

        if (stateIsSearch) {

            view_search_view__icon_search.setImageResource(R.drawable.ic_search)

        } else {

            view_search_view__icon_search.setImageResource(R.drawable.ic_star_not_selected)

        }

    }

    private fun showFavouriteList() {

        presenter.loadFavouriteUsers()

    }

    override fun onStart() {
        super.onStart()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}

interface AdapterDisplaySearchClickListener {
    fun navigateToFullUserInformation(pos: Int)
}

interface SearchDisplayFragment {
    fun loadListOfUsers(list: List<User>)
    fun hideProgressBar()
    fun showErrorMessage(message: String)
    fun showProgressBar()
}
