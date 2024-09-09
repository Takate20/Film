package com.example.film.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the task table.
 */
@Dao
interface FilmDao {

    @Query("SELECT * FROM film")
    fun observeFavorites(): Flow<List<LocalFilm>>

    @Query("SELECT * FROM film WHERE id = :filmId")
    suspend fun getById(filmId: Int): LocalFilm?

    @Query("DELETE FROM film WHERE id = :filmId")
    suspend fun deleteById(filmId: Int): Int

    @Query("SELECT id FROM film")
    fun observeExistingIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: LocalFilm)

    @Transaction
    suspend fun toggleFavorite(film: LocalFilm) {
        val favoriteFilm = getById(film.id)
        if (favoriteFilm == null) {
            insertFilm(film)
        } else {
            deleteById(film.id)
        }
    }
}
