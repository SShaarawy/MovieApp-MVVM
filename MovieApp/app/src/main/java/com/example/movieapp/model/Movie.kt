package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Movies (
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

@Entity(tableName = "movies")
data class Result(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids:List<Int>,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) : Serializable

data class MovieDetails(
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
