package com.irsyaad.dicodingsubmission.thecollection.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.DetailTv
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.DetailDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailDataViewModel
    private lateinit var lang: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        lang= "en-US"
        val id: Int = intent.getIntExtra("idtv", 0)

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ DetailDataViewModel(lang, id) })[DetailDataViewModel::class.java]
        viewModel.getDetailTv().observe(this, Observer {result ->
            viewModel.showLoading.value = false

            if (result != null){

                setAppBar(result)
                setLayout(result)

            } else{
                viewModel.isError.value = true
            }
        })

        isLoading()
        isError()

        setSupportActionBar(toolbar)
        onSwipeRefresh(lang, id)

    }

    private fun setLayout(result: DetailTv){
        txtTitle.text = result.name
        txtRating.text = "${result.vote_average}"
        txtOverview.text = result.overview
        txtOriginalTitle.text = result.original_name
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

    private fun setAppBar(result: DetailTv){
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, i ->
            if (i > -500) {
                collapsingToolbar.title = ""
                toolbar.setNavigationIcon(R.drawable.ic_back_white_24dp)
            } else {
                collapsingToolbar.title = result.name
                toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
            }
        })

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                appBar.visibility = View.GONE
                nestedScroll.visibility = View.GONE
                progressBarTvDetail.visibility = View.VISIBLE
            }else{
                progressBarTvDetail.visibility = View.GONE
                appBar.visibility = View.VISIBLE
                nestedScroll.visibility = View.VISIBLE
            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                appBar.visibility = View.GONE
                nestedScroll.visibility = View.GONE
                progressBarTvDetail.visibility = View.GONE
                errorTvDetail.visibility = View.VISIBLE
                Toast.makeText(this, "Connection to Server Error :(", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun onSwipeRefresh(lang: String, id: Int){
        swipeRefreshTv.setOnRefreshListener {
            viewModel.setDetailTv(lang, id)
            swipeRefreshTv.isRefreshing = false
        }
    }
}
