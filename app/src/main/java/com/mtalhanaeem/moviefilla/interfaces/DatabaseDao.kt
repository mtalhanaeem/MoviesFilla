package com.mtalhanaeem.moviefilla.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtalhanaeem.moviefilla.models.AllMoviesModel
import com.mtalhanaeem.moviefilla.models.MovieDetailsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    /**
     * We can add all Queries here
     * Movies Dao
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMoviesDao(allMoviesModel: AllMoviesModel)

    @Query("SELECT * FROM all_movies_table")
    fun getAllMoviesDao(): Flow<List<AllMoviesModel>>

    /**
     *
     * Movies Details Dao
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesDetailsDao(movieDetailsModel: MovieDetailsModel)

    @Query("SELECT * FROM movie_details_table WHERE number=:movieID")
    fun getMoviesDetailsDao(movieID: String): Flow<List<MovieDetailsModel>>
}