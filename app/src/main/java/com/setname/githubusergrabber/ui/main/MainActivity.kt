package com.setname.githubusergrabber.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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


class MainActivity : AppCompatActivity() {

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

        setContentView(R.layout.activity_main)

    }

    private fun initInjection() {
        App.appComponent.inject(this)
    }

    private fun initNavigator() {
        navigator = SupportAppNavigator(this, R.id.main_container)
    }

    override fun onStart() {
        super.onStart()
        navigatorHolder.setNavigator(navigator)

        changeScreen()

    }

    private fun changeScreen() {
        router.newChain(Screens.SearchDisplayFragmentScreen())
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }


}
