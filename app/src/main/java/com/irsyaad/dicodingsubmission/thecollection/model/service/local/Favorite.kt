package com.irsyaad.dicodingsubmission.thecollection.model.service.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val rating: String,
    val poster: String,
    val type: String
)