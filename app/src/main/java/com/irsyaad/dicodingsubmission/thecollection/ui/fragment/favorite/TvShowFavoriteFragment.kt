package com.irsyaad.dicodingsubmission.thecollection.ui.fragment.favorite


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
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.detail.DetailTvActivity
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ListDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFavoriteFragment : Fragment() {

    private lateinit var viewModel: ListDataViewModel
    private lateinit var favAdapter: FavoriteRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ ListDataViewModel(context!!,"tv")})[ListDataViewModel::class.java]
        isLoading()
        isError()
        viewModel.getDataFavorite().observe(this, Observer {result ->
            if(result != null){
                favAdapter.setData(result)
                if(result.isEmpty()) txtNotFound.visibility = View.VISIBLE

            }
        })

        favAdapter = FavoriteRecyclerAdapter(context!!){
            val detail = Intent(context, DetailTvActivity::class.java)
            detail.putExtra("idTv", it.idData)
            startActivity(detail)
        }

        recyclerViewTv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favAdapter
        }

        onSwipeRefresh()
    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                progressBarTv.visibility = View.VISIBLE
                recyclerViewTv.visibility = View.GONE
                errorTv.visibility = View.GONE
                txtNotFound.visibility = View.GONE

            }else{
                recyclerViewTv.visibility = View.VISIBLE
                progressBarTv.visibility = View.GONE
                errorTv.visibility = View.GONE
                txtNotFound.visibility = View.GONE

            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                errorTv.visibility = View.VISIBLE
                recyclerViewTv.visibility = View.GONE
                progressBarTv.visibility = View.GONE
                Toast.makeText(context, getString(R.string.error_connection), Toast.LENGTH_LONG).show()
                txtNotFound.visibility = View.GONE
            }
        })
    }

    private fun onSwipeRefresh(){
        swipeRefreshTv.setOnRefreshListener {
            viewModel.setListDataFavorite("tv")
            swipeRefreshTv.isRefreshing = false
        }
    }
}
