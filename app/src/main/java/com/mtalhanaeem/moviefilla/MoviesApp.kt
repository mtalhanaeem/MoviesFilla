package com.mtalhanaeem.moviefilla

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.mtalhanaeem.moviefilla.database.AppDatabase
import com.mtalhanaeem.moviefilla.repository.AppRepository
import com.mtalhanaeem.moviefilla.viewmodels.AllMoviesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MoviesApp : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    //private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val databaseAppScope by lazy { AppDatabase.getDatabase(this) }
    val repositoryAppScope by lazy { AppRepository(databaseAppScope.databaseDao()) }

    override fun onCreate() {
        super.onCreate()

        //Initialize Fast Android Networking
        AndroidNetworking.initialize(applicationContext)

        //Starting Koin
        startKoin {
            androidContext(this@MoviesApp)
            modules(listOf(movieModules))
        }
    }

    private val movieModules = module {
        single { databaseAppScope.databaseDao() }
        single { AppRepository(get()) }
        viewModel { AllMoviesViewModel(get()) }
    }
}