package com.setname.githubusergrabber.navigation

import android.support.v4.app.Fragment
import com.setname.githubusergrabber.ui.search.SearchDisplayFragmentImpl
import com.setname.githubusergrabber.ui.user.DisplayUserFragmentImpl
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class SearchDisplayFragmentScreen : SupportAppScreen(){
        override fun getFragment(): Fragment {
            return SearchDisplayFragmentImpl()
        }
    }

    class DisplayUserFragmentScreen : SupportAppScreen(){
        override fun getFragment(): Fragment {
            return DisplayUserFragmentImpl()
        }
    }

}