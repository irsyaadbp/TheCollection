package com.irsyaad.dicodingsubmission.thecollection.model.service

import com.irsyaad.dicodingsubmission.thecollection.model.DataModel
import com.irsyaad.dicodingsubmission.thecollection.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    fun getDataFilm(@Query("api_key") api_key: String, @Query("language") lang: String): Call<DataModel>

    @GET("tv")
    fun getDataTv(@Query("api_key") api_key: String, @Query("language") lang: String): Call<DataModel>
}