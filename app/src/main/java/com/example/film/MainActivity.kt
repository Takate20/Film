package com.example.film

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.film.di.component.ApplicationComponent
import com.example.film.di.component.DaggerApplicationComponent
import com.example.film.films.FilmsScreen
import com.example.film.ui.theme.FilmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationComponent = DaggerApplicationComponent.builder().build()

        enableEdgeToEdge()
        setContent {
            FilmTheme {
                FilmNavGraph()
            }
        }
    }
}

