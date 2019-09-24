package com.irsyaad.dicodingsubmission.thecollection.ui.activity.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.irsyaad.dicodingsubmission.thecollection.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.searchMovie -> {
                val movie = Intent(this, SearchFilmActivity::class.java)
                startActivity(movie)
            }
            R.id.searchTv -> {
                val tv = Intent(this, SearchTvActivity::class.java)
                startActivity(tv)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar.title = getString(R.string.search)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        searchMovie.setOnClickListener(this)
        searchTv.setOnClickListener(this)
    }
}
