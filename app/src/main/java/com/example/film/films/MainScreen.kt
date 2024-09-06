package com.example.film.films

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.film.FilmNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    /***
     * Define a list of routes if you wanna show it for specific routes.
     * For example I wanna show the app bar only for these routes
    val bottomNavRoutes = listOf(
    LeafScreen.Home.route,
    LeafScreen.Search.route,
    LeafScreen.Favorites.route,
    LeafScreen.Profile.route,
    )
     ***/
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            FilmNavGraph(navController = navController)
        }
    }
}