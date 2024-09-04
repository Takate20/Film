package com.example.film.network

import com.example.film.data.remote.models.NetworkFilm
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow


interface FilmDataSource {
    fun getFilms(): Flow<Resource<List<NetworkFilm>>>
}