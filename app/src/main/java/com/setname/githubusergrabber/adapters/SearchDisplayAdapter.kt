package com.setname.githubusergrabber.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.entities.repository.User

class SearchDisplayAdapter(val list: List<User>) : RecyclerView.Adapter<SearchDisplayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.model_rv_display_search, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {

        holder.setData(list[pos])

    }


    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.model_rv_display_search_iv_user)
        lateinit var userImage:ImageView

        @BindView(R.id.model_rv_display_search_tv_username)
        lateinit var userName:TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun setData(user: User){

            setName(user.login)
            setImage(user.image)

        }

        private fun setName(name:String){
            userName.text = name
        }

        private fun setImage(url:String){
            Glide.with(view).load(url).into(userImage)
        }

    }


}