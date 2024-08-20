package com.example.film.util

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}