package com.irsyaad.dicodingsubmission.thecollection.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.ui.fragment.favorite.FilmFavoriteFragment
import com.irsyaad.dicodingsubmission.thecollection.ui.fragment.FilmFragment
import com.irsyaad.dicodingsubmission.thecollection.ui.fragment.favorite.TvShowFavoriteFragment
import com.irsyaad.dicodingsubmission.thecollection.ui.fragment.TvShowFragment

class ViewPagerAdapter(private val context : Context, fragmentManager : FragmentManager, private val from : String) : FragmentPagerAdapter(fragmentManager) {

    private val pagesFavorite = listOf(
        FilmFavoriteFragment(),
        TvShowFavoriteFragment()
    )

    private val pages = listOf(
        FilmFragment(),
        TvShowFragment()
    )

    override fun getItem(position: Int): Fragment {
        return when(from){
            "main" -> pages[position]
            else -> pagesFavorite[position]
        }
    }

    override fun getCount(): Int {
        return if (from == "main") pages.size
        else pagesFavorite.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> context.getString(R.string.movie)
            else -> context.getString(R.string.tvshow)
        }
    }
}