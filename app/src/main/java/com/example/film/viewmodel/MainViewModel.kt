package com.example.film.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film.data.model.Film
import com.example.film.data.model.toExternal
import com.example.film.data.remote.models.NetworkFilm
import com.example.film.domain.FilmRepository
import com.example.film.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FilmUiState(
    val films: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUserRepository: FilmRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow(null)
    private val _films = MutableStateFlow<Resource<List<NetworkFilm>>>(Resource.Loading)

    init {
        viewModelScope.launch {
            mainUserRepository.getFilms().collect { films ->
                Log.d("my log", films.toString())
                _films.value = films
            }
        }
    }

    val state: StateFlow<FilmUiState> = combine(
        _isLoading, _films, _errorMessage
    ) { isLoading, films, errorMessage ->
        when (films) {
            is Resource.Success -> {
                FilmUiState(
                    films = films.data.map(NetworkFilm::toExternal),
                    isLoading = isLoading,
                    userMessage = errorMessage
                )
            }

            is Resource.Error -> {
                FilmUiState(
                    userMessage = films.errorMessage
                )
            }

            Resource.Loading -> {
                FilmUiState(isLoading = true)
            }
        }

    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FilmUiState(isLoading = true)
        )

    fun refresh() {
        _isLoading.value = true
        viewModelScope.launch {
            mainUserRepository.getFilms().collect { film ->
                _films.value = film
            }
            _isLoading.value = false
        }
    }
}