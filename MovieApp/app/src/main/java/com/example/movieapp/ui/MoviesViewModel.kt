package com.example.movieapp.ui

import androidx.lifecycle.*
import com.example.movieapp.model.*
import com.example.movieapp.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(val moviesRepository: MoviesRepository) : ViewModel() {

    private val _movieList = MutableLiveData<Movies>()
    val movieList: LiveData<Movies> = _movieList

    private val _searchMovie = MutableLiveData<Movies>()
    val searchMovie: LiveData<Movies> = _searchMovie

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetail: LiveData<MovieDetails> = _movieDetails

    private val _genres = MutableLiveData<GenreList>()
    val genres: LiveData<GenreList> = _genres

    init {
        getGenres()
        getMovieList()
    }

    fun getMovieList() {
       viewModelScope.launch() {
           val response = moviesRepository.getMovieList()
           if(response.isSuccessful){
               response.body().let {
                   _movieList.value = it
               }
           }
       }
    }

    fun getMovieDetails(movieId: Int){
        viewModelScope.launch() {
            val response = moviesRepository.getMovieDetails(movieId)
            if(response.isSuccessful){
                response.body().let {
                    _movieDetails.value = it
                }
            }
        }
    }

    fun searchMovie(query : String) {
        viewModelScope.launch() {
            val response = moviesRepository.searchMovie(query)
            if(response.isSuccessful) {
                response.body().let {
                    _searchMovie.value = it
                }
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch {
            val response = moviesRepository.getGenres()
            if(response.isSuccessful) {
                response.body()?.let {
                    _genres.value = it
                }
            }
        }
    }

    fun getGenreMoviesList(genreId: String) {
        viewModelScope.launch {
            val response = moviesRepository.getGenreMoviesList(genreId)
            if(response.isSuccessful) {
                response.body().let {
                    _movieList.value = it
                    println("asdasd")
                }
            }
        }
    }


    fun isExists(id: Int) : LiveData<Int> {
        return moviesRepository.isExists(id).asLiveData()
    }

    fun saveMovie(movie: Result) {
        viewModelScope.launch {
            moviesRepository.upsert(movie)
        }
    }

    fun getSavedMovies(): LiveData<List<Result>> {
        return moviesRepository.getSavedMovies().asLiveData()
    }

    fun deleteMovie(movie: Result) {
        viewModelScope.launch {
            moviesRepository.deleteMovie(movie)
        }
    }
}