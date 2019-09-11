package com.irsyaad.dicodingsubmission.thecollection.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.adapter.FavoriteRecyclerAdapter
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.detail.DetailFilmActivity
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ListDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_film_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FilmFavoriteFragment : Fragment() {

    private lateinit var viewModel: ListDataViewModel
    private lateinit var favAdapter: FavoriteRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ ListDataViewModel(context!!,"film")})[ListDataViewModel::class.java]
        isLoading()
        isError()
        viewModel.getDataFavorite().observe(this, Observer {result ->
            if(result != null){
                favAdapter.setData(result)
            }else{
                viewModel.isError.value = true
            }
        })

        favAdapter = FavoriteRecyclerAdapter(context!!){
            val detail = Intent(context, DetailFilmActivity::class.java)
            detail.putExtra("idfilm", it.idData)
            startActivity(detail)
        }

        recyclerViewFilm.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favAdapter
        }

        onSwipeRefresh()

    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                progressBarFilm.visibility = View.VISIBLE
                recyclerViewFilm.visibility = View.GONE
                errorFilm.visibility = View.GONE
            }else{
                recyclerViewFilm.visibility = View.VISIBLE
                progressBarFilm.visibility = View.GONE
                errorFilm.visibility = View.GONE
            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                errorFilm.visibility = View.VISIBLE
                recyclerViewFilm.visibility = View.GONE
                progressBarFilm.visibility = View.GONE
                Toast.makeText(context, "Connection to Server Error :(", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun onSwipeRefresh(){
        swipeRefreshFilm.setOnRefreshListener {
            viewModel.setListDataFavorite("film")
            swipeRefreshFilm.isRefreshing = false
        }
    }

}
