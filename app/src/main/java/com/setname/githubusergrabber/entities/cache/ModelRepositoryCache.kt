package com.setname.githubusergrabber.entities.cache

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "repositories")
data class ModelRepositoryCache(
    @PrimaryKey
    val id: Long,
    @ColumnInfo
    val owner_id: Long,
    @ColumnInfo
    val repository: Repository
)

data class Repository(
    val name: String,
    val description: String,
    val language: String
)