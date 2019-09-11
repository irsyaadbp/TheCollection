package com.irsyaad.dicodingsubmission.thecollection.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        toolbar.title = getString(R.string.favorite)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setViewPager()
    }

    private fun setViewPager(){
        viewPager.adapter = ViewPagerAdapter(this, supportFragmentManager, "favorite")
        tabLayout.setupWithViewPager(viewPager)
    }
}
