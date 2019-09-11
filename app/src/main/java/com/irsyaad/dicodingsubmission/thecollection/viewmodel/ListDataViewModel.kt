package com.irsyaad.dicodingsubmission.thecollection.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_KEY
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.*
import com.irsyaad.dicodingsubmission.thecollection.model.service.local.FavoriteDatabase
import com.irsyaad.dicodingsubmission.thecollection.model.service.network.ApiRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListDataViewModel(private val context: Context, private val parameter : String) : ViewModel() {

    private val service = ApiRepository.getData()
    private var favoriteDatabase: FavoriteDatabase? = null
    private var disposable: CompositeDisposable? = null

    private val listFilm: MutableLiveData<List<ListDetailFilm>> = MutableLiveData()
    private val listTvShow: MutableLiveData<List<ListDetailTv>> = MutableLiveData()

    private val listFavorite: MutableLiveData<List<FavoriteModel>> = MutableLiveData()
//    private val lisTvShowFavorite: MutableLiveData<List<FavoriteModel>> = MutableLiveData()

    var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isError: MutableLiveData<Boolean> = MutableLiveData()

    init {
        favoriteDatabase = FavoriteDatabase.getInstance(context)
        disposable = CompositeDisposable()
    }


    fun getDataFilm(): LiveData<List<ListDetailFilm>> {
        if(listFilm.value == null) setDataFilm(parameter)
        return listFilm
    }

    fun getDataTv(): LiveData<List<ListDetailTv>> {
        if(listTvShow.value == null) setDataTv(parameter)
        return listTvShow
    }

    fun getDataFavorite(): LiveData<List<FavoriteModel>> {
        if (listFavorite.value ==  null) setListDataFavorite(parameter)
        return listFavorite
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

    fun setListDataFavorite(type:String) {
        showLoading.postValue(true)
        disposable!!.add(Observable.fromCallable{favoriteDatabase?.favoriteDao()?.getFavorite(type)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    showLoading.postValue(false)
                    isError.postValue(false)
                    
                    listFavorite.postValue(it)
                    if (it.isEmpty()) Toast.makeText(context, context.getString(R.string.fav_not_found), Toast.LENGTH_SHORT).show()
                },
                {
                    showLoading.postValue(false)
                    isError.postValue(true)
                }
            ))
    }
}
