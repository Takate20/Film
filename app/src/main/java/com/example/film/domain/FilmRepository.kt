package com.example.film.domain

import com.example.film.data.local.LocalFilm
import com.example.film.data.remote.models.NetworkFilm
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    fun getFilmsStream(): Flow<Resource<List<NetworkFilm>>>

    suspend fun toggleFilm(film: LocalFilm)
    suspend fun getFavoriteFilmIdsStream(): Flow<List<Int>>
//
//    fun getFavoriteFilmIds(): List<Int>?
}