package com.setname.githubusergrabber.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.setname.githubusergrabber.entities.cache.ModelFavourite

@Dao
interface DatabaseDao {

    /*@Query("INSERT INTO favourite VALUES(:id, :user)")*/
    @Insert
    fun insertFavourite(modelFavourite: ModelFavourite)

    @Query("SELECT * FROM favourite")
    fun getAllFavourite(): List<ModelFavourite>

    @Query("SELECT COUNT(*) FROM favourite WHERE id = :id")
    fun isExist(id: Long): Long

    @Query("DELETE FROM favourite WHERE id = :id")
    fun deleteFavourite(id: Long)

}