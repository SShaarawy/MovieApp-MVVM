package com.example.movieapp.api

import com.example.movieapp.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val API_KEY = "59cd6896d8432f9c69aed9b86b9c2931"
private const val BASE_URL = "https://api.themoviedb.org/3/"

private const val SORT_BY = "popularity.desc"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MoviesApiService {

    @GET("movie/top_rated?")
    suspend fun getMovies(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movies>

    @GET("search/movie?")
    suspend fun searchMovie(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("query")
        query: String,
        @Query("include_adult")
        includeAdult: Boolean = false
    ) : Response<Movies>

    @GET("movie/{movie_id}?")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: Int,
        @Query("api_key")
        apiKey: String = API_KEY
    ) : Response<MovieDetails>

    @GET("discover/movie?")
    suspend fun getGenreMoviesList(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("with_genres")
        withGenres: String
    ) : Response<Movies>

    @GET("genre/movie/list?")
    suspend fun getGenres(
        @Query("api_key")
        apiKey: String = API_KEY,
    ) : Response<GenreList>

}

object MoviesApi {
    val retrofitService : MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}