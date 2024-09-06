package com.example.film.domain.api

import com.example.film.data.remote.models.FilmResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("3/discover/movie")
    suspend fun getFilms(@Query("api_key") apiKey: String): Response<FilmResult>
}