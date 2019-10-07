package com.irsyaad.dicodingsubmission.thecollection.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.irsyaad.dicodingsubmission.thecollection.model.service.local.FavoriteDatabase
import io.reactivex.disposables.CompositeDisposable

class FavoriteProvider : ContentProvider() {

    private var favoriteDatabase: FavoriteDatabase? = null
    private  var disposable: CompositeDisposable? = null

    companion object{

        private const val AUTHORITY = "com.irsyaad.dicodingsubmission.thecollection"
//        private const val SCHEME = "content"

        private const val TABLE_NAME = "favorite"

//        private val CONTENT_URI = Uri.Builder().scheme(SCHEME)
//            .authority(AUTHORITY)
//            .appendPath(TABLE_NAME)
//            .build()

        private const val FILM = 1
        private const val TV = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/film", FILM)
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/tv", TV)
        }

    }

    override fun onCreate(): Boolean {
        favoriteDatabase = FavoriteDatabase.getInstance(context!!)
        disposable = CompositeDisposable()

        return true
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        arrayString1: Array<String>?,
        string1: String?,
        arrayString2: Array<String>?,
        string2: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            FILM -> favoriteDatabase!!.favoriteDao().getFavoriteProvider("film")
            TV -> favoriteDatabase!!.favoriteDao().getFavoriteProvider("tv")
            else -> null
        }
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<String>?): Int {
        return 0
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<String>?): Int {
        return 0
    }

    override fun getType(p0: Uri): String? {
        return null
    }
}