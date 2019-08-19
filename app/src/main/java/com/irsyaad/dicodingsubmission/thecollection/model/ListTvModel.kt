package com.irsyaad.dicodingsubmission.thecollection.model

import com.google.gson.annotations.SerializedName

data class ListTvModel(
    @SerializedName("results")
    val results: List<ListDetailTv>
)

data class ListDetailTv(
    val id: Int,
    val vote_average: Double,
    val name: String?,
    val poster_path: String?,
    val overview: String?
)

data class DetailTv(
    val vote_count: Int,
    val id: Int,
    val vote_average: Double,
    val name: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    @SerializedName("original_language")
    val language: String?,
    val original_name: String?,
    val overview: String?,
    val runtime: Int,
    val status: String?,
    val adult: Boolean
)
