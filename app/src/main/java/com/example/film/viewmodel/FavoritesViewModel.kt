package com.example.film.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film.data.model.Film
import com.example.film.data.model.toExternal
import com.example.film.data.model.toLocal
import com.example.film.domain.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FavoritesUiState(
    val favorites: List<Film>
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val filmRepository: FilmRepository
) : ViewModel() {
    val favoritesUiState: StateFlow<FavoritesUiState> = favoritesUiState(filmRepository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FavoritesUiState(favorites = listOf())
    )

    private fun favoritesUiState(filmRepository: FilmRepository): Flow<FavoritesUiState> {
        return filmRepository.getFavoritesStream()
            .map { favorites ->
                FavoritesUiState(favorites = favorites.map { it.toExternal() })
            }
    }

    fun toggleFavorite(film: Film) {
        viewModelScope.launch {
            filmRepository.toggleFilm(film.toLocal())
        }
    }
}