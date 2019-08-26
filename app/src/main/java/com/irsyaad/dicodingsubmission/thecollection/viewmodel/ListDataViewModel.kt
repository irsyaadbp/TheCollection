package com.irsyaad.dicodingsubmission.thecollection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_KEY
import com.irsyaad.dicodingsubmission.thecollection.model.ListFilmModel
import com.irsyaad.dicodingsubmission.thecollection.model.ListDetailFilm
import com.irsyaad.dicodingsubmission.thecollection.model.ListDetailTv
import com.irsyaad.dicodingsubmission.thecollection.model.ListTvModel
import com.irsyaad.dicodingsubmission.thecollection.model.service.network.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListDataViewModel(private val lang: String) : ViewModel() {

    private val service = ApiRepository.getData()

    private  val listFilm: MutableLiveData<List<ListDetailFilm>> = MutableLiveData()
    private  val listTvShow: MutableLiveData<List<ListDetailTv>> = MutableLiveData()

    var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isError: MutableLiveData<Boolean> = MutableLiveData()


    fun getDataFilm(): LiveData<List<ListDetailFilm>> {
        if(listFilm.value == null) setDataFilm(lang)
        return listFilm

    }

    fun getDataTv(): LiveData<List<ListDetailTv>> {
        if(listTvShow.value == null) setDataTv(lang)
        return listTvShow

    }

    fun setDataFilm(lang : String) {
        showLoading.postValue(true)
        service.getDataFilm(API_KEY, lang).enqueue(object : Callback<ListFilmModel>{
            override fun onResponse(call: Call<ListFilmModel>, response: Response<ListFilmModel>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()

                listFilm.postValue(data?.results)
            }

            override fun onFailure(call: Call<ListFilmModel>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }
        })
    }

    fun setDataTv(lang: String){
        showLoading.postValue(true)
        service.getDataTv(API_KEY, lang).enqueue(object : Callback<ListTvModel>{
            override fun onResponse(call: Call<ListTvModel>, response: Response<ListTvModel>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()

                listTvShow.postValue(data?.results)
            }

            override fun onFailure(call: Call<ListTvModel>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }
        })
    }
}
