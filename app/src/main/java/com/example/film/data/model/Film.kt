package com.example.film.data.model

import com.example.film.data.remote.models.NetworkFilm

data class Film(
    val id: Int,
    val posterPath: String,
    val title: String,
    val overview: String,
    val voteAverage: Double,
)

fun NetworkFilm.toExternal() = Film(
    id = id,
    posterPath = "https://image.tmdb.org/t/p/w500/$poster_path",
    title = title,
    overview = overview,
    voteAverage = vote_average
)