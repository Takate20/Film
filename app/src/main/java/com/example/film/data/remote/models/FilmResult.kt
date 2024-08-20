package com.example.film.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class FilmResult(
    val results: List<NetworkFilm>,
)