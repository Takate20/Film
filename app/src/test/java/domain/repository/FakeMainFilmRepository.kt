package domain.repository

import com.example.film.data.local.LocalFilm
import com.example.film.data.remote.models.NetworkFilm
import com.example.film.domain.FilmRepository
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class FakeMainFilmRepository : FilmRepository {

    private val observeFilms = MutableStateFlow<Resource<List<NetworkFilm>>>(Resource.Loading)

    private var shouldReturnNetworkError = false

    fun setShoutReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getFilmsStream(): Flow<Resource<List<NetworkFilm>>> = flow {
        if (shouldReturnNetworkError) {
            emit(Resource.Error("error"))
        } else {
            emit(
                Resource.Success(
                    data = listOf(
                        NetworkFilm(
                            id = 1,
                            overview = "overview",
                            posterPath = "posterPath",
                            title = "title"
                        )
                    )
                )
            )
        }
    }

    override fun getFavoritesStream(): Flow<List<LocalFilm>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFilm(film: LocalFilm) {
        TODO("Not yet implemented")
    }

    override fun getFavoriteFilmIdsStream(): Flow<List<Int>> {
        TODO("Not yet implemented")
    }
}