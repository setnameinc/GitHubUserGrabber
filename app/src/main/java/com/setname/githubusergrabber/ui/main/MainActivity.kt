package com.setname.githubusergrabber.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.constants.Navigation
import com.setname.githubusergrabber.navigation.Screens
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named


class MainActivity : MvpAppCompatActivity() {

    private val LAYOUT = R.layout.activity_main
    private val CONTAINER_ID = R.id.main_container

    @Inject
    @field:Named(Navigation.ROUTER_FIRST_LEVEL)
    lateinit var router: Router

    @Inject
    @field:Named(Navigation.NAVIGATION_HOLDER_FIRST_LEVEL)
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInjection()
        initNavigator()

        navigatorHolder.setNavigator(navigator)

        changeScreen()

        setContentView(LAYOUT)

    }

    private fun initInjection() {
        App.appComponent.inject(this)
    }

    private fun initNavigator() {
        navigator = SupportAppNavigator(this, CONTAINER_ID)
    }

    private fun changeScreen() {
        router.newRootScreen(Screens.SearchDisplayFragmentScreen())
    }

    override fun onDestroy() {
        navigatorHolder.removeNavigator()
        super.onDestroy()
    }


}
