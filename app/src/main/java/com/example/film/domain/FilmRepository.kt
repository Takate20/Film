package com.example.film.domain

import com.example.film.data.remote.models.NetworkFilm
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    fun getFilms(): Flow<Resource<List<NetworkFilm>>>
}