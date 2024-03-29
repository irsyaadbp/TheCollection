package com.irsyaad.dicodingsubmission.thecollection.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_KEY
import com.irsyaad.dicodingsubmission.thecollection.model.DetailFilm
import com.irsyaad.dicodingsubmission.thecollection.model.DetailTv
import com.irsyaad.dicodingsubmission.thecollection.model.FavoriteModel
import com.irsyaad.dicodingsubmission.thecollection.model.service.local.FavoriteDatabase
import com.irsyaad.dicodingsubmission.thecollection.model.service.network.ApiRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class DetailDataViewModel(context: Context, private val lang: String, private val id:Int) : ViewModel() {
    private val service = ApiRepository.getData()
    private var favoriteDatabase: FavoriteDatabase? = null
    private var disposable: CompositeDisposable? = null

    private val detailFilm: MutableLiveData<DetailFilm> = MutableLiveData()
    private val detailTvShow: MutableLiveData<DetailTv> = MutableLiveData()

    var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isError: MutableLiveData<Boolean> = MutableLiveData()
    var isFavorite:MutableLiveData<Boolean> = MutableLiveData()

    init {
        favoriteDatabase = FavoriteDatabase.getInstance(context)
        disposable = CompositeDisposable()
    }

    fun getDetailFilm(): LiveData<DetailFilm>{
        if(detailFilm.value == null) setDetailFilm(lang, id)
        return detailFilm
    }

    fun getDetailTv(): LiveData<DetailTv>{
        if(detailTvShow.value == null) setDetailTv(lang, id)
        return detailTvShow
    }

    private fun setDetailFilm(lang: String, id: Int){
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

    private fun setDetailTv(lang: String, id: Int){
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

    fun setFavorite(favoriteModel: FavoriteModel){

        disposable!!.add(Observable.fromCallable{favoriteDatabase?.favoriteDao()?.insert(favoriteModel)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    fun deleteFavorite(id:Int, type:String){
        disposable!!.add(Observable.fromCallable{favoriteDatabase?.favoriteDao()?.deleteFavorite(id,type)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    fun checkFavorite(id:Int, type:String){
        disposable!!.add(Observable.fromCallable{favoriteDatabase?.favoriteDao()?.checkFavoriteById(id,type)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                isFavorite.value = it > 0
            })
    }

    override fun onCleared() {
        super.onCleared()

        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }

    }
}