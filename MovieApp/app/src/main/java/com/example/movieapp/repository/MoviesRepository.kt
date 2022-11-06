package com.example.movieapp.repository

import com.example.movieapp.api.MoviesApi.retrofitService
import com.example.movieapp.db.MovieDatabase
import com.example.movieapp.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MoviesRepository(private val db: MovieDatabase) {

    suspend fun getMovieList(): Response<Movies> {
        return retrofitService.getMovies()
    }

    suspend fun searchMovie(query: String): Response<Movies> {
        return retrofitService.searchMovie(query = query)
    }

    suspend fun getMovieDetails(movieId: Int): Response<MovieDetails> {
        return retrofitService.getMovieDetails(movieId)
    }

    suspend fun getGenreMoviesList(genreId: String): Response<Movies> {
        return retrofitService.getGenreMoviesList(withGenres = genreId)
    }

    suspend fun getGenres(): Response<GenreList> {
        return retrofitService.getGenres()
    }

    fun isExists(id: Int): Flow<Int> {
        return db.getMovieDao().isExists(id)
    }

    suspend fun upsert(movie: Result) {
        db.getMovieDao().upsert(movie)
    }

    fun getSavedMovies(): Flow<List<Result>> {
        return db.getMovieDao().getAllMovies()
    }

    suspend fun deleteMovie(movie: Result) {
        db.getMovieDao().deleteMovie(movie)
    }
}