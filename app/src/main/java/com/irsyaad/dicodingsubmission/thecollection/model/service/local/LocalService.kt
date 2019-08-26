package com.irsyaad.dicodingsubmission.thecollection.model.service.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocalService {
    @Query("SELECT * FROM favorite WHERE type == :type")
    fun getFavorite(type: String): Favorite

    @Insert
    fun insertFavorite(vararg fav: Favorite)

    @Delete
    fun deleteFavorite(fav: Favorite)
}