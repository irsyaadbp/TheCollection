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
import com.irsyaad.dicodingsubmission.thecollection.adapter.TvRecyclerAdapter
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.detail.DetailTvActivity
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ListDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment() {

    private lateinit var viewModel: ListDataViewModel
    private lateinit var lang: String
    private lateinit var filmAdapter: TvRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lang = "en-Us"

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ ListDataViewModel(context!!, lang)})[ListDataViewModel::class.java]

        isLoading()
        isError()

        viewModel.getDataTv().observe(this, Observer { result ->
            if(result != null){
                filmAdapter.setData(result)
            }else{
                viewModel.isError.value = true
            }
        })

        filmAdapter = TvRecyclerAdapter(context!!){
            val detail = Intent(context, DetailTvActivity::class.java)
            detail.putExtra("idTv", it.id)
            startActivity(detail)
        }

        recyclerViewTv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filmAdapter
        }

        onSwipeRefresh()
    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                progressBarTv.visibility = View.VISIBLE
                recyclerViewTv.visibility = View.GONE
                errorTv.visibility = View.GONE
            }else{
                recyclerViewTv.visibility = View.VISIBLE
                progressBarTv.visibility = View.GONE
                errorTv.visibility = View.GONE
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
            }
        })
    }

    private fun onSwipeRefresh(){
        swipeRefreshTv.setOnRefreshListener {
            viewModel.setDataTv(lang)
            swipeRefreshTv.isRefreshing = false
        }
    }

}
