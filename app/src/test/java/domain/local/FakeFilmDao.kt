package domain.local

import com.example.film.data.local.FilmDao
import com.example.film.data.local.LocalFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFilmDao(var localFilms: MutableList<LocalFilm> = mutableListOf()) : FilmDao{
    override fun observeFavorites(): Flow<List<LocalFilm>> = flow {
        emit(localFilms)
    }

    override suspend fun getById(filmId: Int): LocalFilm? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(filmId: Int): Int {
        TODO("Not yet implemented")
    }

    override fun observeExistingIds(): Flow<List<Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFilm(film: LocalFilm) {
        localFilms.add(film)
    }
}
