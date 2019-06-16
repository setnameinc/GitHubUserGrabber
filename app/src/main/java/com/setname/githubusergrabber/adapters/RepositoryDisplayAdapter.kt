package com.setname.githubusergrabber.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.setname.githubusergrabber.R
import com.setname.githubusergrabber.entities.cache.Repository

class RepositoryDisplayAdapter (val list: List<Repository>):
    RecyclerView.Adapter<RepositoryDisplayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.model_rv_display_repository, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {

        holder.setData(list[pos])

    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun setData(repository: Repository) {

            setName(repository.name)
            setDescription(repository.description)
            setLanguage(repository.language)

        }

        private fun setName(name: String) {
            view.findViewById<TextView>(R.id.model_rv_display_repository__tv_username).text = name
        }

        private fun setDescription(descrition:String){

            view.findViewById<TextView>(R.id.model_rv_display_repository__tv_desc).text = descrition

        }

        private fun setLanguage(language:String){

            view.findViewById<TextView>(R.id.model_rv_display_repository__tv_language).text = language


        }

    }


}