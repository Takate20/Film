package domain.repository

import com.example.film.data.local.LocalFilm
import com.example.film.data.model.Film
import com.example.film.data.model.toLocal
import com.example.film.data.model.toNetwork
import com.example.film.data.remote.models.NetworkFilm
import com.example.film.domain.FilmRepository
import com.example.film.domain.repository.MainFilmRepository
import com.example.film.util.Resource
import domain.data.remote.FakeFilmDataSource
import domain.local.FakeFilmDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.junit.Before

class FakeMainFilmRepository : FilmRepository {

    private var shouldThrowError = false

    private val _films = MutableStateFlow(listOf<Film>())

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }

    override fun getFilmsStream(): Flow<Resource<List<NetworkFilm>>> = flow {
        if (shouldThrowError) {
            throw Exception("test error")
        } else {
            _films
        }
    }

    override fun getFavoritesStream(): Flow<List<LocalFilm>> =
        _films.map { films -> films.filter { it.isFavorite }.map { it.toLocal() } }

    override suspend fun toggleFilm(film: LocalFilm) {
        _films.update { films ->
            films.map { existingFilm ->
                if (existingFilm.id == film.id) {
                    existingFilm.copy(isFavorite = !existingFilm.isFavorite)
                } else {
                    existingFilm
                }
            }
        }
    }

    override fun getFavoriteFilmIdsStream(): Flow<List<Int>> {
        return _films.map { films -> films.map { it.id } }
    }
}