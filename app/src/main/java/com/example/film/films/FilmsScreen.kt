package com.example.film.films

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.film.data.model.Film
import com.example.film.viewmodel.FilmUiState
import com.example.film.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmsScreen(
    filmUiState: FilmUiState,
    onFilmClick: (Film) -> Unit,
    toggleFavorite: (Film) -> Unit
) {
    val state = rememberPullToRefreshState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        PullToRefreshBox(
            state = state,
            isRefreshing = filmUiState.isLoading,
            onRefresh = {},
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(filmUiState.films) { film ->
                    ElevatedCard(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onFilmClick(film) }
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = film.posterPath,
                                    contentDescription = "user img",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .size(70.dp)
                                        .clip(CircleShape)
                                )
                                Text(
                                    text = film.title,
                                )
                                IconButton(onClick = { toggleFavorite(film) }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = "star",
                                        tint = if (film.isFavorite) Color.Red else Color.Black
                                    )
                                }

                            }
                            Text(
                                text = film.overview,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun FilmsScreenPreview() {
    FilmsScreen(
        toggleFavorite = {},
        onFilmClick = {},
        filmUiState = FilmUiState(
            listOf(
                Film(
                    id = 1,
                    posterPath = "https://image.tmdb.org/t/p/w500/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
                    title = "Borderlands",
                    overview = "some long overview",
                    voteAverage = 10.0,
                    isFavorite = false
                ),
                Film(
                    id = 2,
                    posterPath = "https://image.tmdb.org/t/p/w500/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
                    title = "Deadpool Wolverine",
                    overview = "some looooooooong overview",
                    voteAverage = 10.0,
                    isFavorite = true
                )
            )
        )
    )
}