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
import kotlinx.coroutines.flow.flow
import org.junit.Before

class FakeMainFilmRepository : FilmRepository {

    private val film1 = Film(id = 1, overview = "ove", posterPath = "po", title = "ti", isFavorite = false)
    private val film2 = Film(id = 2, overview = "ove", posterPath = "po", title = "ti", isFavorite = false)
    private val film3 = Film(id = 3, overview = "ove", posterPath = "po", title = "ti", isFavorite = false)
    private val remoteFilms = listOf(film1, film2, film3).toNetwork()
    private val localFilms = listOf(film1).toLocal()
    private lateinit var filmDataSource: FakeFilmDataSource
    private lateinit var filmDao: FakeFilmDao

    private lateinit var mainFilmRepository: FilmRepository

    @Before
    fun createRepository() {
        filmDataSource = FakeFilmDataSource(remoteFilms.toMutableList())
        filmDao = FakeFilmDao(localFilms.toMutableList())
        mainFilmRepository = MainFilmRepository(filmDataSource, filmDao)
    }

    override fun getFilmsStream(): Flow<Resource<List<NetworkFilm>>> = flow {

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