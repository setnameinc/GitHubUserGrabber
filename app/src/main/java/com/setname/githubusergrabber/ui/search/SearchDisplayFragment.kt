package com.setname.githubusergrabber.ui.search

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.adapters.SearchDisplayAdapter
import com.setname.githubusergrabber.constants.Navigation
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.interfaces.BaseFragmentInteractions
import com.setname.githubusergrabber.interfaces.NavigationInteractions
import com.setname.githubusergrabber.interfaces.NetworkListenerInteractions
import com.setname.githubusergrabber.interfaces.RecyclerViewInteractions
import com.setname.githubusergrabber.navigation.Screens
import com.setname.githubusergrabber.presenter.search.ISearchPresenter
import com.setname.githubusergrabber.presenter.search.SearchPresenter
import com.setname.githubusergrabber.rxutils.RxSearchObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

class SearchDisplayFragment : MvpAppCompatFragment(), ISearchDisplayFragment, SearchDisplayFragmentView {

    private val disposableBag:CompositeDisposable = CompositeDisposable()
    private val TAG = SearchDisplayFragment::class.java.simpleName

    override fun initPresenter() {

    }

    private val LAYOUT = R.layout.fragment_display_search
    private val CONTAINER_ID = R.id.main_container

    @InjectPresenter
    lateinit var presenterI: SearchPresenter

    @ProvidePresenter
    fun provideSearchPresenter():SearchPresenter = SearchPresenter()

    @Inject
    @field:Named(Navigation.ROUTER_SECOND_LEVEL)
    lateinit var router: Router

    @Inject
    @field:Named(Navigation.NAV_HOLDER_SECOND_LEVEL)
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var localeView: View //temporary solution for save state
    var onCreateView = true //temporary solution for save state
    var onViewCreated = true //temporary solution for save state
    var onErrorState = false

    private lateinit var navigator: Navigator

    private var list: MutableList<User> = arrayListOf()
    private lateinit var adapter: SearchDisplayAdapter

    private lateinit var searchField: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var errorView: ImageView

    override fun initInjection() {
        App.appComponent.inject(this)
    }

    override fun initNavigator() {
        navigator = object : SupportAppNavigator(activity, CONTAINER_ID) {
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

    override fun initNetworkListener() {

        presenterI.initNetworkListener()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        initInjection()
        initNavigator()
        initPresenter()

        navigatorHolder.setNavigator(navigator)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.i(TAG, "onCreateView")

        if (onCreateView) {
            localeView = inflater.inflate(LAYOUT, container, false)

            searchField = localeView.findViewById(R.id.view_search_view__et_search)
            progressBar = localeView.findViewById(R.id.view_search_view__pb)
            errorView = localeView.findViewById(R.id.view_search_view__eb)
            // the views aren't working correctly (lateinit Exception), so must use the vars

            onCreateView = false
        }

        return localeView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated")

        if (onViewCreated) {

            initNetworkListener()

            initSearchListeners()
            initRecyclerView()

            loadFavouriteList()

            onViewCreated = false

        }

    }

    override fun initSearchListeners() {

        val rxSearchObservable = RxSearchObservable()

        val disposableIcon = rxSearchObservable
            .fromView(searchField)
            .debounce(100, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                if (!onErrorState) {
                    showProgressBar()

                    if (it.isNotEmpty()) {

                        initSearchIconByNetworkState(stateIsSearch = true)

                    } else {

                        initSearchIconByNetworkState(stateIsSearch = false)

                    }

                }

            }

        disposableBag.add(disposableIcon)

        val disposableTextListener = rxSearchObservable
            .fromView(searchField)
            .debounce(1200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {

                    presenterI.loadListOfUsers(searchField.text.toString())

                } else {

                    presenterI.loadFavouriteUsers()

                }
            }

        disposableBag.add(disposableTextListener)

    }

    override fun initRecyclerView() {

        val fragment = Screens.DisplayUserFragmentScreen()

        adapter = SearchDisplayAdapter(list,
            object : AdapterDisplaySearchClickListener {

                override fun navigateToFullUserInformation(pos: Int) {

                    presenterI.loadCurrentUser(list[pos])
                    router.navigateTo(Screens.DisplayUserFragmentScreen())

                }

            })

        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        fragment_display_search_rv.apply {

            adapter = this@SearchDisplayFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutAnimation = controller

        }

    }

    override fun initSearchIconByNetworkState(stateIsSearch: Boolean) {

        if (stateIsSearch) {

            view_search_view__icon_search.setImageResource(R.drawable.ic_search)

        } else {

            view_search_view__icon_search.setImageResource(R.drawable.ic_star_not_selected)

        }

    }

    override fun loadFavouriteList() {

        presenterI.loadFavouriteUsers()

    }

    override fun startShowFavouriteList(list: List<User>) {
        this.list.clear()
        this.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun showSuccess() {
        hideProgressBar()

    }

    override fun showErrorMessage(message: String) {
        hideProgressBar()

    }

    override fun showErrorView(message: String) {

        errorView.visibility = View.VISIBLE

        errorView.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_error_start))
        ((errorView.drawable) as AnimatedVectorDrawable).start()

        errorView.setOnClickListener { Toast.makeText(context, message, Toast.LENGTH_LONG).show() }

        onErrorState = true

    }

    override fun hideErrorView() {

        errorView.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_error_end))
        ((errorView.drawable) as AnimatedVectorDrawable).start()

        Handler().postDelayed({ errorView.visibility = View.GONE }, 500L) //too lazy to think about the right solution

        onErrorState = false

    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        navigatorHolder.removeNavigator()
        Log.i(TAG, "onDestroy")
        disposableBag.clear()
        presenterI.clearDisposableBag()
    }

    override fun onPause() {
        Log.i(TAG, "onPause")
        super.onPause()
    }

}

interface AdapterDisplaySearchClickListener {
    fun navigateToFullUserInformation(pos: Int)
}

interface ISearchDisplayFragment :
    BaseFragmentInteractions,
    NavigationInteractions,
    NetworkListenerInteractions {

    fun initSearchListeners()
    fun initSearchIconByNetworkState(stateIsSearch: Boolean)

}

@StateStrategyType(OneExecutionStateStrategy::class)
interface SearchDisplayFragmentView : MvpView,
    SearchListInteractions,
    RecyclerViewInteractions,
    ErrorView {

    fun loadFavouriteList()
    fun showErrorMessage(message: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun showSuccess()

}

interface SearchListInteractions {

    fun startShowFavouriteList(list: List<User>)

}

interface ErrorView {

    fun showErrorView(message: String)
    fun hideErrorView()

}

