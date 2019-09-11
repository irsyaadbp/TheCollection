package com.irsyaad.dicodingsubmission.thecollection.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.DetailTv
import com.irsyaad.dicodingsubmission.thecollection.model.FavoriteModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.DetailDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailDataViewModel
    private lateinit var lang: String

    private lateinit var data: DetailTv

    private var favorite: Boolean = false
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        lang= "en-US"
        val id: Int = intent.getIntExtra("idTv", 0)

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ DetailDataViewModel(this, lang, id) })[DetailDataViewModel::class.java]
        viewModel.getDetailTv().observe(this, Observer {result ->
            viewModel.showLoading.value = false

            if (result != null){

                setAppBar(result)
                setLayout(result)

                data = result

                viewModel.checkFavorite(result.id, "tv")

            } else{
                viewModel.isError.value = true
            }
        })

        isLoading()
        isError()

        setSupportActionBar(toolbar)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.favorite_menu, menu)
        isFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.om_favorite -> {
                viewModel.isFavorite.value = !favorite
                if (favorite) {
                    viewModel.setFavorite(FavoriteModel(data.id, data.name.toString(), data.overview.toString(), data.vote_average.toString(), data.poster_path.toString(),"tv"))
                    Toast.makeText(this, getString(R.string.favorite), Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteFavorite(data.id, "tv")
                    Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun isFavorite(){
        viewModel.isFavorite.observe(this, Observer {status ->
            favorite = status
            appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, i ->
                when {
                    favorite -> menu.getItem(0).icon = getDrawable(R.drawable.ic_favorite_pink_24dp)
                    else -> {
                        if (i > -500) menu.getItem(0).icon = getDrawable(R.drawable.ic_favorite_border_white_24dp)
                        else menu.getItem(0).icon = getDrawable(R.drawable.ic_favorite_border_black_24dp)
                    }
                }
            })
        })
    }
}
