package com.irsyaad.dicodingsubmission.thecollection.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.irsyaad.dicodingsubmission.thecollection.ui.fragment.FilmFragment
import com.irsyaad.dicodingsubmission.thecollection.ui.fragment.TvShowFragment

class ViewPagerAdapter(fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val pages = listOf(
        FilmFragment(),
        TvShowFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> "Movie"
            else -> "TV Show"
        }
    }
}