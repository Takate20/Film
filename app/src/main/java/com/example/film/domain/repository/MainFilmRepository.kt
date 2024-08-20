package com.example.film.domain.repository

import com.example.film.data.remote.models.NetworkFilm
import com.example.film.domain.FilmRepository
import com.example.film.network.FilmDataSource
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainFilmRepository @Inject constructor(
    val randomUserDataSource: FilmDataSource
): FilmRepository {
    override fun getFilms(): Flow<Resource<List<NetworkFilm>>> = randomUserDataSource.getFilms()
}