package com.setname.githubusergrabber.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.setname.githubusergrabber.App
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.contants.Navigation
import com.setname.githubusergrabber.navigation.Screens
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
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
        navigator = object : SupportAppNavigator(this, R.id.main_container) {
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

    override fun onStart() {
        super.onStart()
        navigatorHolder.setNavigator(navigator)

        changeScreen()

    }

    private fun changeScreen() {
        router.newRootScreen(Screens.SearchDisplayFragmentScreen())
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }


}
