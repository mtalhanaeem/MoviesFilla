package com.mtalhanaeem.moviefilla.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_movies_table")
data class AllMoviesModel(
    @PrimaryKey(autoGenerate = true) val idAllMovieModel: Int,
    @ColumnInfo(name = "name") val nameAllMovieModel: String,
    @ColumnInfo(name = "image") val imageAllMovieModel: String,
    @ColumnInfo(name = "number") val numberAllMovieModel: String
)