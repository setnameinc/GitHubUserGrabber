package com.setname.githubusergrabber.entities.cache

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favourite")
data class ModelFavourite(
    @PrimaryKey
    val id: Long,
    @ColumnInfo
    val user: ModelUserDatabase
)