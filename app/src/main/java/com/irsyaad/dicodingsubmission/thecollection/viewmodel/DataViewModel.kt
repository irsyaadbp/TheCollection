package com.irsyaad.dicodingsubmission.thecollection.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_KEY
import com.irsyaad.dicodingsubmission.thecollection.model.DataModel
import com.irsyaad.dicodingsubmission.thecollection.model.Results
import com.irsyaad.dicodingsubmission.thecollection.model.service.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel(private val lang: String) : ViewModel() {

    private val service = ApiRepository.getData()
    private val listFilm: MutableLiveData<List<Results>> by lazy {
        MutableLiveData<List<Results>>().also {
            setDataFilm(lang)
        }
    }


    fun getDataFilm(): LiveData<List<Results>> {
        return listFilm
    }

    private fun setDataFilm(lang : String) {
//        var list:
        service.getDataFilm(API_KEY, lang).enqueue(object : Callback<Results>{
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                val data = response.body()
                Log.d("uye", "${data}")

//                list = data?
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.d("uye", "${t.message}")
            }
        })

//        return list
    }
}
