package com.example.film.viewmodel

import com.example.film.data.model.Film
import domain.repository.FakeMainFilmRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FilmViewModelTest {

    private lateinit var viewModel: FilmViewModel


    @Before
    fun setup() {
        viewModel = FilmViewModel(FakeMainFilmRepository())
    }

    @Test
    fun `insert film with empty field return error`() {
        viewModel.toggleFavorite(Film(1, posterPath = "1", title = "", overview = "overview", isFavorite = true))
    }
}