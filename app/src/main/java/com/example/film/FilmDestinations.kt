package com.example.film

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.film.FilmDestinationsArgs.FILM_DETAILS_ARG
import com.example.film.FilmScreen.FILM_DETAILS

sealed class FilmOuterDestinations(var route: String, val icon: ImageVector?, var title: String) {
    object Home : FilmOuterDestinations("Home", Icons.Rounded.Home, "Home")
    object Favorites : FilmOuterDestinations("Favorites", Icons.Rounded.List, "Favorites")
}

object FilmScreen {
    const val FILM_DETAILS = "filmDetails"
}

object FilmDestinationsArgs {
    const val FILM_DETAILS_ARG = "filmArg"
}

object FilmDestinations {
    const val FILM_DETAILS_ROUTE = "$FILM_DETAILS?$FILM_DETAILS_ARG={$FILM_DETAILS_ARG}"
}

class FilmNavigationActions(private val navController: NavController) {
    fun navigateToFilmDetails(filmJson: String) {
        navController.navigate("film_detail?film=${filmJson}")
    }
}