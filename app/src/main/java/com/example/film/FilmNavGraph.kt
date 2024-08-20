package com.example.film

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.film.data.model.Film
import com.example.film.films.FilmScreen
import com.example.film.films.FilmsScreen
import com.google.gson.Gson

@Composable
fun FilmNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "film_list"
    ) {
        composable("film_list") {
            FilmsScreen(
                onFilmClick = { film ->
                    val filmJson = Gson().toJson(film)
                    navController.navigate("film_detail?film=${filmJson}")
                }
            )
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