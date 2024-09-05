package com.example.film

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.film.data.model.Film
import com.example.film.films.BottomNavigationBar
import com.example.film.films.Favorites
import com.example.film.films.FilmScreen
import com.example.film.films.FilmsScreen
import com.google.gson.Gson

@Composable
fun FilmNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = FilmOuterDestinations.Home.route,
    navActions: FilmNavigationActions = remember(navController) {
        FilmNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = FilmOuterDestinations.Home.route,
            Modifier.padding(paddingValues = innerPadding)
        ) {
            composable(FilmOuterDestinations.Home.route) {
                FilmsScreen(
                    onFilmClick = { film ->
                        val filmJson = Gson().toJson(film)
                        navActions.navigateToFilmDetails(filmJson)
                    }
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
}
