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

    @GET("search/movie")
    fun getSearchFilm(@Query("api_key") api_key: String, @Query("language") lang: String, @Query("query") query: String): Call<ListFilmModel>

    @GET("discover/movie")
    fun getReleaseFilmToday(@Query("api_key") api_key: String, @Query("primary_release_date.gte") dateGte: String, @Query("primary_release_date.lte") dateLte: String): Call<ListFilmModel>

    @GET("discover/tv")
    fun getDataTv(@Query("api_key") api_key: String, @Query("language") lang: String): Call<ListTvModel>

    @GET("tv/{tv_id}")
    fun getDetailTv(@Path("tv_id") tv_id: Int, @Query("api_key") api_key: String, @Query("language") lang: String): Call<DetailTv>

    @GET("search/tv")
    fun getSearchTv(@Query("api_key") api_key: String, @Query("language") lang: String, @Query("query") query: String): Call<ListTvModel>
}