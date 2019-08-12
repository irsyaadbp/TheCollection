package com.irsyaad.dicodingsubmission.thecollection.model

import com.google.gson.annotations.SerializedName

data class TvModel(
    @SerializedName("results")
    val results: List<DetailTv>
)

data class DetailTv(
    val vote_count: Int,
    val id: Int,
    val vote_average: Double,
    val name: String?,
    val poster_path: String?,
    val original_language: String?,
    val original_name: String?,
    val backdrop_path: String?,
    val adult: Boolean,
    val overview: String?,
    val release_date: String?
)