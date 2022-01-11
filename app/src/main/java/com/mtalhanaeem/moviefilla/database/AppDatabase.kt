package com.mtalhanaeem.moviefilla.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mtalhanaeem.moviefilla.interfaces.DatabaseDao
import com.mtalhanaeem.moviefilla.models.AllMoviesModel
import com.mtalhanaeem.moviefilla.models.MovieDetailsModel

@Database(
    entities = [AllMoviesModel::class, MovieDetailsModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "movies_databases"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}