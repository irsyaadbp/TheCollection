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
    private  val listFilm: MutableLiveData<List<Results>> = MutableLiveData()
    private  val listTvShow: MutableLiveData<List<Results>> = MutableLiveData()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isError: MutableLiveData<Boolean> = MutableLiveData()


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
        showLoading.postValue(true)
        service.getDataFilm(API_KEY, lang).enqueue(object : Callback<DataModel>{
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()

                listFilm.postValue(data?.results)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }
        })
    }

    fun setDataTv(lang: String){
        showLoading.postValue(true)
        service.getDataTv(API_KEY, lang).enqueue(object : Callback<DataModel>{
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()
                listTvShow.postValue(data?.results)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }
        })
    }
}
