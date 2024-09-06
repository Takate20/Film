package com.example.film

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.film.data.model.Film
import com.example.film.films.Favorites
import com.example.film.films.FilmScreen
import com.example.film.films.FilmsScreen
import com.example.film.viewmodel.FilmViewModel
import com.google.gson.Gson

@Composable
fun FilmNavGraph(
    navController: NavHostController,
    navActions: FilmNavigationActions = remember(navController) {
        FilmNavigationActions(navController)
    }
) {
    NavHost(
        navController,
        startDestination = FilmOuterDestinations.Home.route,
    ) {
        composable(FilmOuterDestinations.Home.route) {
            val viewModel = hiltViewModel<FilmViewModel>()
            val state = viewModel.filmUiState.collectAsStateWithLifecycle().value
            FilmsScreen(
                filmUiState = state,
                onFilmClick = { film ->
                    val filmJson = Gson().toJson(film)
                    navActions.navigateToFilmDetails(filmJson)
                },
                toggleFavorite = viewModel::toggleFavorite
            )
        }
        composable(FilmOuterDestinations.Favorites.route) {
            Favorites()
        }

        composable(
            route = "film_detail?film={film}",
            arguments = listOf(
                navArgument("film") {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("film")
            val movie = Gson().fromJson(movieJson, Film::class.java)
            FilmScreen(movie)
        }
    }
}

