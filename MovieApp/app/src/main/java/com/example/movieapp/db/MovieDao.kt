package com.example.movieapp.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.movieapp.model.Result



@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Result)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Result>>
    /* Because of the Flow return type, Room also runs the query on the background thread.
     You don't need to explicitly make it a suspend function and call inside a coroutine scope.*/

    @Delete
    suspend fun deleteMovie(movie: Result)
}