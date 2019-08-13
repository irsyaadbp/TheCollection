package com.irsyaad.dicodingsubmission.thecollection.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_KEY
import com.irsyaad.dicodingsubmission.thecollection.model.FilmModel
import com.irsyaad.dicodingsubmission.thecollection.model.DetailFilm
import com.irsyaad.dicodingsubmission.thecollection.model.DetailTv
import com.irsyaad.dicodingsubmission.thecollection.model.TvModel
import com.irsyaad.dicodingsubmission.thecollection.model.service.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel(private val lang: String) : ViewModel() {

    private val service = ApiRepository.getData()
    private  val listFilm: MutableLiveData<List<DetailFilm>> = MutableLiveData()
    private  val listTvShow: MutableLiveData<List<DetailTv>> = MutableLiveData()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isError: MutableLiveData<Boolean> = MutableLiveData()


    fun getDataFilm(): LiveData<List<DetailFilm>> {
        if(listFilm.value == null) setDataFilm(lang)
        return listFilm

    }

    fun getDataTv(): LiveData<List<DetailTv>> {
        if(listTvShow.value == null) setDataTv(lang)
        return listTvShow

    }

    fun setDataFilm(lang : String) {
        showLoading.postValue(true)
        service.getDataFilm(API_KEY, lang).enqueue(object : Callback<FilmModel>{
            override fun onResponse(call: Call<FilmModel>, response: Response<FilmModel>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()

                listFilm.postValue(data?.results)
            }

            override fun onFailure(call: Call<FilmModel>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }
        })
    }

    private fun setDataTv(lang: String){
        showLoading.postValue(true)
        service.getDataTv(API_KEY, lang).enqueue(object : Callback<TvModel>{
            override fun onResponse(call: Call<TvModel>, response: Response<TvModel>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()
                Log.d("uye", "${data?.results}")
                listTvShow.postValue(data?.results)
            }

            override fun onFailure(call: Call<TvModel>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }
        })
    }
}
