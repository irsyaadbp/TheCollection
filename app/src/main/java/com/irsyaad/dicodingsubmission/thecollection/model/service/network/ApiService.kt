package com.irsyaad.dicodingsubmission.thecollection.model.service.network

import com.irsyaad.dicodingsubmission.thecollection.model.DetailFilm
import com.irsyaad.dicodingsubmission.thecollection.model.DetailTv
import com.irsyaad.dicodingsubmission.thecollection.model.ListFilmModel
import com.irsyaad.dicodingsubmission.thecollection.model.ListTvModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    fun getDataFilm(@Query("api_key") api_key: String, @Query("language") lang: String): Call<ListFilmModel>

    @GET("movie/{movie_id}")
    fun getDetailFilm(@Path("movie_id") movie_id: Int, @Query("api_key") api_key: String, @Query("language") lang: String): Call<DetailFilm>

    @GET("discover/tv")
    fun getDataTv(@Query("api_key") api_key: String, @Query("language") lang: String): Call<ListTvModel>

    @GET("tv/{tv_id}")
    fun getDetailTv(@Path("tv_id") tv_id: Int, @Query("api_key") api_key: String, @Query("language") lang: String): Call<DetailTv>
}