package com.example.movieapp.ui

import androidx.lifecycle.*
import com.example.movieapp.model.Details
import com.example.movieapp.model.Movie
import com.example.movieapp.model.Result
import com.example.movieapp.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(val moviesRepository: MoviesRepository) : ViewModel() {

    private val _movieList = MutableLiveData<Movie>()
    val movieList: LiveData<Movie> = _movieList

    private val _searchMovie = MutableLiveData<Movie>()
    val searchMovie: LiveData<Movie> = _searchMovie

    private val _movieDetails = MutableLiveData<Details>()
    val movieDetail: LiveData<Details> = _movieDetails

    init {
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
            val response = moviesRepository.searchNews(query)
            if(response.isSuccessful) {
                response.body().let {
                    _searchMovie.value = it
                }
            }
        }
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