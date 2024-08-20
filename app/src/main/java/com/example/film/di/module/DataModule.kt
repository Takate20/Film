package com.example.film.di.module

import com.example.film.domain.FilmRepository
import com.example.film.domain.repository.MainFilmRepository
import com.example.film.network.FilmDataSource
import com.example.film.network.FilmDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindMainFilmRepository(repository: MainFilmRepository): FilmRepository

    @Singleton
    @Binds
    abstract fun bindFilmDataSource(dataSourceImpl: FilmDataSourceImpl): FilmDataSource
}