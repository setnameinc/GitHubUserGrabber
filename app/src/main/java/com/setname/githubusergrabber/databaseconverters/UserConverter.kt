package com.setname.githubusergrabber.databaseconverters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.setname.githubusergrabber.entities.cache.ModelUserDatabase
import com.setname.githubusergrabber.entities.repository.User

class UserConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromString(value: String): ModelUserDatabase = Gson().fromJson(value, object : TypeToken<ModelUserDatabase>() {}.type)

        @TypeConverter
        @JvmStatic
        fun toString(value: ModelUserDatabase): String = Gson().toJson(value)

    }

}