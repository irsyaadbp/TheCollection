package com.irsyaad.dicodingsubmission.thecollection.adapter

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService

import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.FavoriteModel
import com.irsyaad.dicodingsubmission.thecollection.model.service.local.FavoriteDatabase
import com.irsyaad.dicodingsubmission.thecollection.ui.widget.FilmFavoriteWidget

import java.util.ArrayList
import com.bumptech.glide.Glide

class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var mWidgetItems = ArrayList<String>()
    private var favContainer = ArrayList<FavoriteModel>()
    private var favoriteDatabase: FavoriteDatabase? = null

    override fun onCreate() {
        Log.d("stack", "di load dunk")
        favoriteDatabase = FavoriteDatabase.getInstance(context)
    }

    override fun onDataSetChanged() {
        Log.d("stack", "data set change it")
        Log.d("broadcast", "data set change it")

        if(favContainer.size != 0 && mWidgetItems.size != 0){
            favContainer = ArrayList()
            mWidgetItems = ArrayList()
        }

        val identityToken = Binder.clearCallingIdentity()
        favContainer.addAll(favoriteDatabase!!.favoriteDao().getFavorite("film"))
        Binder.restoreCallingIdentity(identityToken)

        for (item in favContainer){
            mWidgetItems.add("https://image.tmdb.org/t/p/w185${item.poster}")
        }

        Log.d("broadcast", "favcontainer $favContainer")
        Log.d("broadcast", "mwidget $mWidgetItems")

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        Log.d("stack", "getCount")
        return mWidgetItems.size
    }

    override fun getViewAt(i: Int): RemoteViews {
        Log.d("stack", "getViewAt")
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
//        val poster = favContainer.

            val bitmap = Glide.with(context)
                .asBitmap()
                .load(mWidgetItems[i])
                .submit(200, 250)
                .get()

            rv.setImageViewBitmap(R.id.imageView, bitmap)

        Log.d("stack", "getViewAt ${mWidgetItems[i]}")

        val extras = Bundle()
        extras.putInt(FilmFavoriteWidget.EXTRA_ITEM, i)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        Log.d("stack", "getLoadingView")

        return null
    }

    override fun getViewTypeCount(): Int {
        Log.d("stack", "getViewTypeCount")

        return 1
    }

    override fun getItemId(i: Int): Long {
        Log.d("stack", "getItemId")

        return 0
    }

    override fun hasStableIds(): Boolean {
        Log.d("stack", "hasStableIds")

        return false
    }
}
