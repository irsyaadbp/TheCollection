package com.irsyaad.dicodingsubmission.thecollection.ui.activity.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.adapter.TvRecyclerAdapter
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.detail.DetailTvActivity
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ListDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search_tv.*

class SearchTvActivity : AppCompatActivity() {

    private lateinit var viewModel: ListDataViewModel
    private val lang: String = "en-US"
    private lateinit var TvAdapter: TvRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tv)

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ ListDataViewModel(this,lang)})[ListDataViewModel::class.java]

        isLoading()
        isError()

        search()

        viewModel.getDataSearchTv().observe(this@SearchTvActivity, Observer { result ->
            if(result != null){
                TvAdapter.setData(result)
                if(result.isEmpty()) Toast.makeText(this, getString(R.string.search_not_found), Toast.LENGTH_SHORT).show()
            }
        })

        TvAdapter = TvRecyclerAdapter(this){
            val detail = Intent(this, DetailTvActivity::class.java)
            detail.putExtra("idTv", it.id)
            startActivity(detail)
        }

        recyclerViewSearchTv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TvAdapter
        }
    }

    private fun search(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.onActionViewExpanded()
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setDataSearchTv(newText, lang)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setDataSearchTv(query, lang)
                return true
            }

        })
    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                progressBarTv.visibility = View.VISIBLE
                recyclerViewSearchTv.visibility = View.GONE
                errorTv.visibility = View.GONE
            }else{
                recyclerViewSearchTv.visibility = View.VISIBLE
                progressBarTv.visibility = View.GONE
                errorTv.visibility = View.GONE
            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                errorTv.visibility = View.VISIBLE
                recyclerViewSearchTv.visibility = View.GONE
                progressBarTv.visibility = View.GONE
                Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show()
            }
        })
    }
}
