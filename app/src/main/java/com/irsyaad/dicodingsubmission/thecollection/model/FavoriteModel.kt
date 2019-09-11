package com.irsyaad.dicodingsubmission.thecollection.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteModel(
    @ColumnInfo(name = "id_data") val idData : Int,
    val title: String,
    val overview: String,
    val rating: String,
    val poster: String,
    val type: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)