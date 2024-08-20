package com.example.film.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkFilm(
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)