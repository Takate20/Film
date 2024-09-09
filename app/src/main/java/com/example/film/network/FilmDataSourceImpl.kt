package com.example.film.network

import com.example.film.BuildConfig
import com.example.film.data.remote.models.NetworkFilm
import com.example.film.domain.api.FilmApi
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilmDataSourceImpl @Inject constructor(
    private val randomUserApi: FilmApi
) : FilmDataSource {

    override fun getFilms(): Flow<Resource<List<NetworkFilm>>> = flow {
        try {
            val response = randomUserApi.getFilms(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let { filmResult ->
                    emit(Resource.Success(data = filmResult.results))
                }
            } else {
                emit(Resource.Error("is not successful getting users"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(errorMessage = "error getting users"))
        }
    }
}