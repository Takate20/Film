package com.example.film.di.module

import android.content.Context
import androidx.room.Room
import com.example.film.data.local.FilmDao
import com.example.film.data.local.FilmDatabase
import com.example.film.domain.FilmRepository
import com.example.film.domain.repository.MainFilmRepository
import com.example.film.data.remote.FilmDataSource
import com.example.film.data.remote.FilmDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMainFilmRepository(repository: MainFilmRepository): FilmRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindFilmDataSource(dataSourceImpl: FilmDataSourceImpl): FilmDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): FilmDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FilmDatabase::class.java,
            "Film.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFilmDao(database: FilmDatabase): FilmDao = database.filmDao()
}