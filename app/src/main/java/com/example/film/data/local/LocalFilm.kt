package com.example.film.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "film"
)
data class LocalFilm(
    @PrimaryKey val id: Int,
    var title: String,
    var overview: String,
    var posterPath: String
)
