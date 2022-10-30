package com.example.movieapp.repository

import com.example.movieapp.api.MoviesApi.retrofitService
import com.example.movieapp.db.MovieDatabase
import com.example.movieapp.model.Details
import com.example.movieapp.model.Movie
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import com.example.movieapp.model.Result

class MoviesRepository(private val db: MovieDatabase) {

    suspend fun getMovieList(): Response<Movie> {
        return retrofitService.getMovies()
    }
    suspend fun searchNews(query: String): Response<Movie> {
        return retrofitService.searchMovie(query = query)
    }
    suspend fun getMovieDetails(movieId: Int): Response<Details> {
        return retrofitService.getMovieDetails(movieId)
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