package com.setname.githubusergrabber.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.setname.githubusergrabber.entities.cache.ModelFavourite
import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache

@Dao
interface DatabaseDao {

    @Insert
    fun insertFavourite(modelFavourite: ModelFavourite)

    @Insert
    fun insertRepository(modelRepositoryCache: ModelRepositoryCache)

    @Query("SELECT * FROM favourite")
    fun getAllFavourites(): List<ModelFavourite>

    @Query("SELECT * FROM repositories WHERE owner_id = :ownerId")
    fun getAllRepositories(ownerId: Long):List<ModelRepositoryCache>

    @Query("SELECT COUNT(*) FROM favourite WHERE id = :id")
    fun isExistFavourite(id: Long): Long

    @Query("SELECT COUNT(*) FROM repositories WHERE owner_id = :idOwner")
    fun isExistRepository(idOwner: Long): Long

    @Query("DELETE FROM favourite WHERE id = :id")
    fun deleteFavourite(id: Long)

    @Query("DELETE FROM repositories WHERE owner_id = :ownerId")
    fun deleteRepository(ownerId: Long)

}