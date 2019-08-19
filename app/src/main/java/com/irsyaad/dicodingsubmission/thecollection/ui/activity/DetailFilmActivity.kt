package com.irsyaad.dicodingsubmission.thecollection.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowId
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.DetailFilm
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.DetailDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_film.*

class DetailFilmActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailDataViewModel
    private lateinit var lang: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        lang = "en-Us"
        val id: Int = intent.getIntExtra("idfilm", 0)

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ DetailDataViewModel(lang, id) })[DetailDataViewModel::class.java]
        viewModel.getDetailFilm().observe(this, Observer {result ->
            if (result != null){

                setAppBar(result)
                setLayout(result)

                viewModel.showLoading.value = false
            } else{
                viewModel.showLoading.value = false
                viewModel.isError.value = true
            }
        })

        isLoading()
        isError()

        setSupportActionBar(toolbar)
        onSwipeRefresh(lang, id)
    }

    private fun setLayout(result: DetailFilm){
        txtTitle.text = result.title
        txtRating.text = "${result.vote_average}"
        txtOverview.text = result.overview
        txtOriginalTitle.text = result.original_title
        txtLanguage.text = result.language
        txtStatus.text = result.status
        txtRuntime.text = "${result.runtime}m"
        txtLanguage.text = result.language

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w185${result.poster_path}")
            .centerCrop()
            .into(imgPoster)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${result.backdrop_path}")
            .centerCrop()
            .into(imgBackground)

        if(result.adult){
            txtAdult.visibility = View.VISIBLE
            txtNotAdult.visibility = View.GONE
        }else {
            txtAdult.visibility = View.GONE
            txtNotAdult.visibility = View.VISIBLE
        }
    }

    private fun setAppBar(result: DetailFilm){
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, i ->
            if (i > -500) {
                collapsingToolbar.title = ""
                toolbar.setNavigationIcon(R.drawable.ic_back_white_24dp)
            } else {
                collapsingToolbar.title = result.title
                toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
            }
        })
    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                coordinatorLayout.visibility = View.GONE
                constraintStatus.visibility = View.VISIBLE
                progressBarFilmDetail.visibility = View.VISIBLE
            }else{
                constraintStatus.visibility = View.GONE
                progressBarFilmDetail.visibility = View.GONE
                coordinatorLayout.visibility = View.VISIBLE
            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                coordinatorLayout.visibility = View.GONE
                constraintStatus.visibility = View.VISIBLE
                progressBarFilmDetail.visibility = View.GONE
                errorFilmDetail.visibility = View.VISIBLE
                Toast.makeText(this, "Connection to Server Error :(", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun onSwipeRefresh(lang: String, id: Int){
        swipeRefreshFilm.setOnRefreshListener {
            viewModel.setDetailFilm(lang, id)
            swipeRefreshFilm.isRefreshing = false
        }
    }
}
