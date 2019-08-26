package com.irsyaad.dicodingsubmission.thecollection.model.service.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "id_data")var name: String,
    @ColumnInfo(name = "type")var nim: String
)