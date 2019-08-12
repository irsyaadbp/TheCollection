package com.irsyaad.dicodingsubmission.thecollection.model.service

import com.irsyaad.dicodingsubmission.thecollection.model.FilmModel
import com.irsyaad.dicodingsubmission.thecollection.model.TvModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    fun getDataFilm(@Query("api_key") api_key: String, @Query("language") lang: String): Call<FilmModel>

    @GET("tv")
    fun getDataTv(@Query("api_key") api_key: String, @Query("language") lang: String): Call<TvModel>
}