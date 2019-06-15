package com.setname.githubusergrabber.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class SearchDisplayFragmentImpl : Fragment(), SearchDisplayFragment {

    @Inject
    @field:Named(Navigation.ROUTER_FIRST_LEVEL)
    lateinit var router: Router

    @Inject
    lateinit var presenter: SearchDisplayPresenter

    @Inject
    lateinit var list: MutableList<User>

    private lateinit var adapter: SearchDisplayAdapter

    private lateinit var searchField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_display_search, container, false)
        searchField = view.findViewById(R.id.view_search_view__et_search)
        // the view_search_view_et_search isn't works correctly(lateinit Exception), so must use the searchField var
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()
        initSearchListener()
        initRecyclerView()

        showFavouriteList()

    }

    override fun loadListOfUsers(list: List<User>) {
        this.list.clear()
        this.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

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

    private fun showFavouriteList(){
        presenter.loadFavouriteUsers()
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
