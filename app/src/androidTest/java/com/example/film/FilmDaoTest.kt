package com.example.film


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.film.data.local.FilmDao
import com.example.film.data.local.FilmDatabase
import com.example.film.data.local.LocalFilm
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilmDaoTest {

    private lateinit var database: FilmDatabase
    private lateinit var dao: FilmDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FilmDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.filmDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun toggleFavoriteRemove() = runTest {
        val localFilm1 = LocalFilm(id=1, title = "title", overview = "overview", posterPath = "posterPath")
        val localFilm2 = LocalFilm(id=2, title = "title", overview = "overview", posterPath = "posterPath")
        dao.insertFilm(localFilm1)
        dao.insertFilm(localFilm2)

        dao.toggleFavorite(localFilm1)

        val allFilms = dao.observeFavorites().take(1).toList()

        assertThat(allFilms).containsExactly(listOf(localFilm2))
    }

    @Test
    fun insertFilmOnConflictReplace() = runTest {
        val localFilm1 = LocalFilm(id=1, title = "title", overview = "overview", posterPath = "posterPath")
        val localFilm2 = LocalFilm(id=2, title = "title", overview = "overview", posterPath = "posterPath")

        val localFilm3 = LocalFilm(id=2, title = "title", overview = "overview", posterPath = "posterPath")

        dao.insertFilm(localFilm1)
        dao.insertFilm(localFilm2)

        dao.insertFilm(localFilm3)

        val allFilms = dao.observeFavorites().take(1).toList()

        assertThat(allFilms).containsExactly(listOf(localFilm1, localFilm3))
    }

    @Test
    fun toggleFavoriteAdd() = runTest {
        val localFilm1 = LocalFilm(id=1, title = "title", overview = "overview", posterPath = "posterPath")
        val localFilm2 = LocalFilm(id=2, title = "title", overview = "overview", posterPath = "posterPath")

        val localFilm3 = LocalFilm(id=3, title = "title", overview = "overview", posterPath = "posterPath")

        dao.insertFilm(localFilm1)
        dao.insertFilm(localFilm2)

        dao.toggleFavorite(localFilm3)

        val allFilms = dao.observeFavorites().take(1).toList()

        assertThat(allFilms).containsExactly(listOf(localFilm1, localFilm2, localFilm3))
    }

    @Test
    fun observeFavoritesIds() = runTest {
        val localFilm1 = LocalFilm(id=1, title = "title", overview = "overview", posterPath = "posterPath")
        val localFilm2 = LocalFilm(id=2, title = "title", overview = "overview", posterPath = "posterPath")

        dao.insertFilm(localFilm1)
        dao.insertFilm(localFilm2)

        val allFilms = dao.observeExistingIds().take(1).toList()

        assertThat(allFilms).containsExactly(listOf(1, 2))
    }
}