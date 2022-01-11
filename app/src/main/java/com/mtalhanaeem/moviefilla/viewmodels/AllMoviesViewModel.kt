package com.mtalhanaeem.moviefilla.viewmodels

import androidx.lifecycle.*
import com.mtalhanaeem.moviefilla.models.AllMoviesModel
import com.mtalhanaeem.moviefilla.models.MovieDetailsModel
import com.mtalhanaeem.moviefilla.repository.AppRepository
import kotlinx.coroutines.launch

class AllMoviesViewModel(private val appRepository: AppRepository) : ViewModel() {

    /**
     * AllMovies ViewModel
     *
     */
    // Using LiveData and caching what getAllMoviesVM returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val getAllMoviesVM: LiveData<List<AllMoviesModel>> =
        appRepository.getAllMoviesRepoRoom.asLiveData()

    val getAllMoviesServerVM: LiveData<List<AllMoviesModel>> =
        appRepository.getAllMoviesRepoServer().asLiveData()

    /*Launching a new coroutine to insert the data in a non-blocking way*/
    fun insertAllMoviesVM(allMoviesModel: AllMoviesModel) = viewModelScope.launch {
        appRepository.insertAllMoviesRepo(allMoviesModel)
    }


    /**
     * Movie Details ViewModel
     *
     */
    fun getMovieDetailsVM(movieID: String): LiveData<List<MovieDetailsModel>> {
        return appRepository.getMovieDetailsRepoRoom(movieID).asLiveData()
    }

    fun getMovieDetailsServerVM(movieID: String): LiveData<List<MovieDetailsModel>> {
        return appRepository.getMovieDetailsServer(movieID).asLiveData()
    }

    fun insertMovieDetailsVM(movieDetailsModel: MovieDetailsModel) = viewModelScope.launch {
        appRepository.insertMovieDetailsRepo(movieDetailsModel)
    }
}