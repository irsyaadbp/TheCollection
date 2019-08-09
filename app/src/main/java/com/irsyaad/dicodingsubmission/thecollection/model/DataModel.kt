package com.irsyaad.dicodingsubmission.thecollection.model

import com.google.gson.annotations.SerializedName

data class DataModel (
//    val page: Int,
    @SerializedName("results")
    val results: List<Results>
)

data class Results(
    val vote_count: Int,
    val id: Int,
    val vote_average: Double,
    val title: String?,
    val poster_path: String?,
    val original_language: String?,
    val original_title: String?,
    val backdrop_path: String?,
    val adult: Boolean,
    val overview: String?,
    val release_date: String?
)