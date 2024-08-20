package com.example.film.domain.api

import com.example.film.data.remote.models.FilmResult
import retrofit2.Response
import retrofit2.http.GET

interface FilmApi {
    @GET("3/discover/movie?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb")
    suspend fun getFilms(): Response<FilmResult>
}