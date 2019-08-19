package com.irsyaad.dicodingsubmission.thecollection.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.DetailDataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory

class DetailTvActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailDataViewModel
    private lateinit var lang: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        lang= "en-US"
        val id: Int = intent.getIntExtra("idtv", 0)

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ DetailDataViewModel(lang, id) })[DetailDataViewModel::class.java]
        isLoading()
        isError()
        onSwipeRefresh()
        viewModel.getDetailFilm().observe(this, Observer {result ->
            if (result != null){
                Log.d("uye", "$result")
                viewModel.showLoading.value = false
            } else{
                viewModel.showLoading.value = true
            }
        })

    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                //TODO show loading
            }else{
                //TODO hide loading
            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                //TODO show error response
            }
        })
    }

    private fun onSwipeRefresh(){

    }
}
