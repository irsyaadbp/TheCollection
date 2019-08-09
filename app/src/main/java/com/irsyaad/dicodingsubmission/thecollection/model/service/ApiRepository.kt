package com.irsyaad.dicodingsubmission.thecollection.model.service

import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    fun getData(): ApiService{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}