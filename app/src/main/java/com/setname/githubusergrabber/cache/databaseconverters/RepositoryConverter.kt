package com.setname.githubusergrabber.cache.databaseconverters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.setname.githubusergrabber.entities.cache.Repository

class RepositoryConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromString(value: String): Repository = Gson().fromJson(value, object : TypeToken<Repository>() {}.type)

        @TypeConverter
        @JvmStatic
        fun toString(value: Repository): String = Gson().toJson(value)

    }

}