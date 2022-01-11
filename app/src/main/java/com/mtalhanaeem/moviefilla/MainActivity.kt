package com.mtalhanaeem.moviefilla

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.mtalhanaeem.moviefilla.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)
        supportActionBar?.hide()

        val navController = findNavController(R.id.navHostFragment)

        appBarConfiguration = AppBarConfiguration(navController.graph)
    }
}