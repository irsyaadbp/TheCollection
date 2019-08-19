package com.irsyaad.dicodingsubmission.thecollection.model

import com.google.gson.annotations.SerializedName

data class ListFilmModel (
    @SerializedName("results")
    val results: List<ListDetailFilm>
)

data class ListDetailFilm(
    val id: Int,
    val vote_average: Double,
    val title: String?,
    val poster_path: String?,
    val overview: String?
)

data class DetailFilm (
    val vote_count: Int,
    val id: Int,
    val vote_average: Double,
    val title: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    @SerializedName("original_language")
    val language: String?,
    val original_title: String?,
    val overview: String?,
    val runtime: Int,
    val status: String?,
    val adult: Boolean
)
