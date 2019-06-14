package com.setname.githubusergrabber.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.adapters.SearchDisplayAdapter
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.presenter.search.SearchDisplayPresenter
import kotlinx.android.synthetic.main.fragment_display_search.*

class SearchDisplayFragmentImpl : Fragment(), SearchDisplayFragment {

    private var list:MutableList<User> = arrayListOf()
    private val presenter = SearchDisplayPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_display_search, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

    }

    private fun initRecyclerView() {

        fragment_display_search_rv.apply {

            adapter = SearchDisplayAdapter(list)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }

    }

}

interface SearchDisplayFragment{



}
