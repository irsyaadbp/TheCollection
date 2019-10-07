package com.irsyaad.dicodingsubmission.thecollection.ui.activity.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.irsyaad.dicodingsubmission.thecollection.R

import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_search_film.*
import android.content.Context.SEARCH_SERVICE
import android.app.SearchManager

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.irsyaad.dicodingsubmission.thecollection.adapter.FilmRecyclerAdapter
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.detail.DetailFilmActivity
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ListDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory


class SearchFilmActivity : AppCompatActivity() {

    private lateinit var viewModel: ListDataViewModel
    private val lang: String = "en-US"
    private lateinit var filmAdapter: FilmRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_film)

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ ListDataViewModel(this,lang)})[ListDataViewModel::class.java]

        isLoading()
        isError()

        search()

        viewModel.getDataSearchFilm().observe(this@SearchFilmActivity, Observer { result ->

            if(result != null){
                filmAdapter.setData(result)

                if(result.isEmpty()) Toast.makeText(this, getString(R.string.search_not_found), Toast.LENGTH_SHORT).show()
            }
        })

        filmAdapter = FilmRecyclerAdapter(this){
            val detail = Intent(this, DetailFilmActivity::class.java)
            detail.putExtra("idfilm", it.id)
            startActivity(detail)
        }

        recyclerViewSearchFilm.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filmAdapter
        }
    }

    private fun search(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.onActionViewExpanded()
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setDataSearchFilm(newText, lang)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setDataSearchFilm(query, lang)
                return true
            }

        })
    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                progressBarFilm.visibility = View.VISIBLE
                recyclerViewSearchFilm.visibility = View.GONE
                errorFilm.visibility = View.GONE
            }else{
                recyclerViewSearchFilm.visibility = View.VISIBLE
                progressBarFilm.visibility = View.GONE
                errorFilm.visibility = View.GONE
            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                errorFilm.visibility = View.VISIBLE
                recyclerViewSearchFilm.visibility = View.GONE
                progressBarFilm.visibility = View.GONE
                Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show()
            }
        })
    }

}
