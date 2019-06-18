package com.setname.githubusergrabber.cache.databaseconverters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.setname.githubusergrabber.entities.cache.ModelUserDatabase

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