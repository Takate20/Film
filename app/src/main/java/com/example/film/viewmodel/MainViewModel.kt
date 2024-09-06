package com.example.film.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film.data.model.Film
import com.example.film.data.model.toExternal
import com.example.film.data.model.toLocal
import com.example.film.domain.FilmRepository
import com.example.film.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    val filmUiState: StateFlow<FilmUiState> = filmUiState(mainUserRepository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FilmUiState(isLoading = true)
    )

    private fun filmUiState(
        mainUserRepository: FilmRepository
    ): Flow<FilmUiState> {
        val filmsStream = mainUserRepository.getFilmsStream()

        val favoritesIdsStream = mainUserRepository.getFavoriteFilmIdsStream()

        return combine(
            _isLoading, filmsStream, _errorMessage, favoritesIdsStream
        ) { isLoading, films, errorMessage, favoritesIds ->
            when (films) {
                is Resource.Success -> {
                    val filmsResult = films.data.map { film ->
                        film.toExternal(
                            isFavorite = favoritesIds.contains(film.id)
                        )
                    }
                    FilmUiState(
                        films = filmsResult,
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
    }

    fun toggleFavorite(film: Film) {
        viewModelScope.launch {
            mainUserRepository.toggleFilm(film.toLocal())
        }
    }
}