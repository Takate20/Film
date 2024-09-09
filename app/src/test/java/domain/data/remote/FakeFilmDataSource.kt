package domain.data.remote

import com.example.film.data.local.LocalFilm
import com.example.film.data.remote.models.NetworkFilm
import com.example.film.data.remote.FilmDataSource
import com.example.film.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFilmDataSource(
    var remoteFilms: MutableList<NetworkFilm> = mutableListOf()
) : FilmDataSource {
    private var shouldReturnNetworkError = false

    fun setShoutReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getFilms(): Flow<Resource<List<NetworkFilm>>> = flow {
        if (shouldReturnNetworkError) {
            emit(Resource.Error("error"))
        } else {
            emit(Resource.Success(data = remoteFilms))
        }
    }
}