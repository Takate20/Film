package com.example.film.domain.repository

import com.example.film.data.local.FilmDao
import com.example.film.data.local.LocalFilm
import com.example.film.data.remote.models.NetworkFilm
import com.example.film.domain.FilmRepository
import com.example.film.network.FilmDataSource
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainFilmRepository @Inject constructor(
    private val filmDataSource: FilmDataSource,
    private val filmLocalSource: FilmDao
): FilmRepository {
    override fun getFilmsStream(): Flow<Resource<List<NetworkFilm>>> = filmDataSource.getFilms()

    override suspend fun toggleFilm(film: LocalFilm) {
        filmLocalSource.toggleFavorite(film)
    }

    override fun getFavoriteFilmIdsStream(): Flow<List<Int>> {
        return filmLocalSource.observeExistingIds()
    }
}