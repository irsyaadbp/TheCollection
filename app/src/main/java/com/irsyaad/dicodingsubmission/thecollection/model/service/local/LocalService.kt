package com.irsyaad.dicodingsubmission.thecollection.model.service.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.irsyaad.dicodingsubmission.thecollection.model.FavoriteModel

@Dao
interface LocalService {
    @Query("SELECT * FROM favorite WHERE type == :type")
    fun getFavorite(type: String): List<FavoriteModel>

    @Query("SELECT COUNT(*) FROM favorite WHERE id_data = :idData AND type = :type")
    fun getFavoriteById(idData: Int, type: String) : Int

    @Insert
    fun insert(fav: FavoriteModel)

    @Delete
    fun deleteFavorite(fav: FavoriteModel)
}