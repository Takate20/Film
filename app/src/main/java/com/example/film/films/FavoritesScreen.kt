package com.example.film.films

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import coil.compose.AsyncImage
import com.example.film.data.model.Film
import com.example.film.viewmodel.FavoritesUiState
import com.example.film.viewmodel.FilmUiState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(
    favoritesUiState: FavoritesUiState,
    toggleFavorite: (Film) -> Unit
) {
    val state = rememberPullToRefreshState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        PullToRefreshBox(
            state = state,
            isRefreshing = false,
            onRefresh = {},
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            if (favoritesUiState.favorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Favorites")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    items(favoritesUiState.favorites) { favorite ->
                        ElevatedCard(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            modifier = Modifier
                                .fillMaxWidth()
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
                                        model = favorite.posterPath,
                                        contentDescription = "user img",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .size(70.dp)
                                            .clip(CircleShape)
                                    )
                                    Text(
                                        text = favorite.title,
                                    )
                                    IconButton(onClick = { toggleFavorite(favorite) }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Star,
                                            contentDescription = "star",
                                            tint = if (favorite.isFavorite) Color.Red else Color.Black
                                        )
                                    }

                                }
                                Text(
                                    text = favorite.overview,
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
}

@Preview
@Composable
fun FavoritesScreenPreview() {
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
                    isFavorite = true
                ),
                Film(
                    id = 2,
                    posterPath = "https://image.tmdb.org/t/p/w500/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
                    title = "Deadpool Wolverine",
                    overview = "some looooooooong overview",
                    isFavorite = true
                )
            )
        )
    )
}