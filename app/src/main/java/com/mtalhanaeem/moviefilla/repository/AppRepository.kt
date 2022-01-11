package com.mtalhanaeem.moviefilla.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.mtalhanaeem.moviefilla.interfaces.DatabaseDao
import com.mtalhanaeem.moviefilla.models.AllMoviesModel
import com.mtalhanaeem.moviefilla.models.MovieDetailsModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONObject


class AppRepository(private val databaseDao: DatabaseDao) {

    /**
     * We can add all the repositories here
     * All Movies Repository
     */
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val getAllMoviesRepoRoom: Flow<List<AllMoviesModel>> = databaseDao.getAllMoviesDao()

    private var allMoviesList = MutableLiveData<List<AllMoviesModel>>()
    private var allMoviesListServer = mutableListOf<AllMoviesModel>()

    @DelicateCoroutinesApi
    fun getAllMoviesRepoServer(): Flow<List<AllMoviesModel>> {

        AndroidNetworking.get("https://api.themoviedb.org/3/movie/popular?api_key=adc60343796d0843d5fd21076902a71f")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response
                    val jsonArray = response.getJSONArray("results")

                    for (i in 0 until jsonArray.length()) {
                        val singleObject = jsonArray.getJSONObject(i)

                        val id = singleObject.getString("id")
                        val name = singleObject.getString("original_title")
                        val image = singleObject.getString("poster_path")

                        allMoviesListServer.add(AllMoviesModel(0, name, image, id))

                        GlobalScope.launch {
                            insertAllMoviesRepo(AllMoviesModel(0, name, image, id))
                        }

                        allMoviesList.postValue(allMoviesListServer)
                    }
                }

                override fun onError(error: ANError) {
                }
            })

        return allMoviesList.asFlow()
    }


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllMoviesRepo(allMoviesModel: AllMoviesModel) {
        databaseDao.insertAllMoviesDao(allMoviesModel)
    }


    /**
     *
     * Movie Details Repository
     */
    fun getMovieDetailsRepoRoom(movieID: String): Flow<List<MovieDetailsModel>> {
        return databaseDao.getMoviesDetailsDao(movieID)
    }

    private var movieDetails = MutableLiveData<List<MovieDetailsModel>>()

    @DelicateCoroutinesApi
    fun getMovieDetailsServer(movieID: String): Flow<List<MovieDetailsModel>> {

        AndroidNetworking.get("https://api.themoviedb.org/3/movie/$movieID?api_key=adc60343796d0843d5fd21076902a71f")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response

                    val image = response.getString("poster_path")
                    val name = response.getString("original_title")
                    val tagline = response.getString("tagline")

                    val date = response.getString("release_date")
                    val rating = response.getString("vote_average")
                    val status = response.getString("status")
                    val duration = response.getString("runtime")
                    val overview = response.getString("overview")
                    val id = response.getString("id")

                    GlobalScope.launch {
                        insertMovieDetailsRepo(
                            MovieDetailsModel(
                                0,
                                image,
                                name,
                                tagline,
                                date,
                                rating,
                                status,
                                duration,
                                overview,
                                id
                            )
                        )
                    }

                    movieDetails.postValue(
                        listOf(
                            MovieDetailsModel(
                                0,
                                image,
                                name,
                                tagline,
                                date,
                                rating,
                                status,
                                duration,
                                overview,
                                id
                            )
                        )
                    )
                }

                override fun onError(error: ANError) {
                }
            })

        return movieDetails.asFlow()
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMovieDetailsRepo(movieDetailsModel: MovieDetailsModel) {
        databaseDao.insertMoviesDetailsDao(movieDetailsModel)
    }


}