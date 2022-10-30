package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.db.MovieDatabase
import com.example.movieapp.repository.MoviesRepository


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    lateinit var viewModel : MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.news_Nav_Host_Fragment) as NavHostFragment
        navController = navHostFragment.navController

        val moviesRepository = MoviesRepository(MovieDatabase.getDatabase(this))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(moviesRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(MoviesViewModel::class.java)

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}