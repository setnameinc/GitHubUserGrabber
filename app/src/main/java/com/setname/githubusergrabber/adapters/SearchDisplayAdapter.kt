package com.setname.githubusergrabber.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.entities.repository.User
import com.setname.githubusergrabber.ui.search.AdapterDisplaySearchClickListener

class SearchDisplayAdapter(private val list: List<User>, private val onDisplaySearchClickListener: AdapterDisplaySearchClickListener) :
    RecyclerView.Adapter<SearchDisplayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.model_rv_display_search, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {

        holder.setData(list[pos])

    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

          onDisplaySearchClickListener.navigateToFullUserInformation(adapterPosition)

        }

        fun setData(user: User) {

            setName(user.login)
            setImage(user.avatar_url)

        }

        private fun setName(name: String) {
            view.findViewById<TextView>(R.id.model_rv_display_search__tv_username).text = name
        }

        private fun setImage(url: String) {
            Glide.with(view)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(view.findViewById(R.id.model_rv_display_search__iv_user))
        }

    }


}