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
    private val listFilm: MutableLiveData<List<Results>> = MutableLiveData()


    fun getDataFilm(): LiveData<List<Results>> {
        if(listFilm.value == null) {
            setDataFilm(lang)
            Log.d("uye2", "load lagi dari viewmodel")
        }else{
            Log.d("uye2", "gak load lagi dari viewmodel dong")
        }
        return listFilm
    }

    fun setDataFilm(lang : String) {
        service.getDataFilm(API_KEY, lang).enqueue(object : Callback<DataModel>{
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                val data = response.body()
                Log.d("uye1", "${data}")
                listFilm.postValue(data?.results)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                Log.d("uye1", "${t.message}")
            }
        })
    }
}
