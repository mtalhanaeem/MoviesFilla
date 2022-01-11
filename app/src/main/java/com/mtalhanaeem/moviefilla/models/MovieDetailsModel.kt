package com.mtalhanaeem.moviefilla.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details_table")
data class MovieDetailsModel(
    @PrimaryKey(autoGenerate = true) val idMovieDetailsModel: Int,
    @ColumnInfo(name = "image") val imageMovieDetailsModel: String,
    @ColumnInfo(name = "name") val nameMovieDetailsModel: String,
    @ColumnInfo(name = "tagline") val tagMovieDetailsModel: String,
    @ColumnInfo(name = "date") val dateMovieDetailsModel: String,
    @ColumnInfo(name = "rating") val ratingMovieDetailsModel: String,
    @ColumnInfo(name = "status") val statusMovieDetailsModel: String,
    @ColumnInfo(name = "duration") val durationMovieDetailsModel: String,
    @ColumnInfo(name = "overview") val overviewMovieDetailsModel: String,
    @ColumnInfo(name = "number") val numberMovieDetailsModel: String
)