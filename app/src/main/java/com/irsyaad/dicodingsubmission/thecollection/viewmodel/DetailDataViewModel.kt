package com.irsyaad.dicodingsubmission.thecollection.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_KEY
import com.irsyaad.dicodingsubmission.thecollection.model.DetailFilm
import com.irsyaad.dicodingsubmission.thecollection.model.DetailTv
import com.irsyaad.dicodingsubmission.thecollection.model.service.ApiRepository
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class DetailDataViewModel(private val lang: String, private val id:Int) : ViewModel() {
    private val service = ApiRepository.getData()

    private val detailFilm: MutableLiveData<DetailFilm> = MutableLiveData()
    private val detailTvShow: MutableLiveData<DetailTv> = MutableLiveData()

    var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isError: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailFilm(): LiveData<DetailFilm>{
        if(detailFilm.value == null) setDetailFilm(lang, id)
        return detailFilm
    }

    fun getDetailTv(): LiveData<DetailTv>{
        if(detailFilm.value == null) setDetailTv(lang, id)
        return detailTvShow
    }

    fun setDetailFilm(lang: String, id: Int){
        showLoading.postValue(true)
        service.getDetailFilm(id, API_KEY, lang).enqueue(object : Callback<DetailFilm>{
            override fun onResponse(call: Call<DetailFilm>, response: Response<DetailFilm>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()

                Log.d("uye1", "$data")

                detailFilm.postValue(data)
            }

            override fun onFailure(call: Call<DetailFilm>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }

        })

    }

    fun setDetailTv(lang: String, id: Int){
        showLoading.postValue(true)

        service.getDetailTv(id, API_KEY, lang).enqueue(object : Callback<DetailTv>{
            override fun onResponse(call: Call<DetailTv>, response: Response<DetailTv>) {
                showLoading.postValue(false)
                isError.postValue(false)

                val data = response.body()

                Log.d("uye", "$data")

                detailTvShow.postValue(data)
            }

            override fun onFailure(call: Call<DetailTv>, t: Throwable) {
                showLoading.postValue(false)
                isError.postValue(true)
            }

        })

    }
}