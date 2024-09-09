package com.example.film.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFilm(
//    @SerializedName("genre_ids")
//    val genreIds: List<Int>,
    val id: Int,
//    val original_title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String,
//    val vote_average: Double,
//    val vote_count: Int
)