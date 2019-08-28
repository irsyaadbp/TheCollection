package com.irsyaad.dicodingsubmission.thecollection.model.service.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.irsyaad.dicodingsubmission.thecollection.model.FavoriteModel

@Database(entities = [FavoriteModel::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao() : LocalService
    companion object{
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase? {
            if (INSTANCE == null){
                synchronized(FavoriteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "favoritedata.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}