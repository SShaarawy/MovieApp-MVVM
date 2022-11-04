package com.example.movieapp.model

data class GenreList(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)
