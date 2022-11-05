package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapters.GenresAdapter
import com.example.movieapp.databinding.FragmentMovieOverviewBinding
import com.example.movieapp.model.Genre
import com.example.movieapp.ui.MoviesViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout


class MovieOverviewFragment : Fragment() {
    private lateinit var binding: FragmentMovieOverviewBinding
    private val genresAdapter = GenresAdapter()
    private val args: MovieFragmentArgs by navArgs()

    private val sharedViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieOverviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie

        val collToolbar = requireActivity().findViewById<CollapsingToolbarLayout>(R.id.colltoolbar)
        collToolbar.title = " "

        val bgCollToolbar = requireActivity().findViewById<ImageView>(R.id.bgCollToolbar)
        Glide.with(requireActivity())
            .load("http://image.tmdb.org/t/p/w500/" + movie.poster_path)
            .into(bgCollToolbar)

        binding.tvTitle.text = movie.title
        binding.tvDate.text = "(${movie.release_date.substring(0, 4)})   "+ movie.vote_average
        binding.tvOverview.text = movie.overview
        binding.ratingBar.rating = (movie.vote_average / 2).toFloat()

        binding.rvMovieGenres.apply {
            adapter = genresAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        val idList = settingMovieGenres(movie.genre_ids)
        genresAdapter.differ.submitList(idList)

    }

    private fun settingMovieGenres(genreIds: List<Int>) : List<Genre> {
        val genres = sharedViewModel.genres.value?.genres
        val idList = mutableListOf<Genre>()

        if (genres != null) {
            for (genreId in genreIds) {
                for (genre in genres) {
                    if (genreId == genre.id) {
                        idList.add(genre)
                    }
                }
            }
        }
        return idList
    }
}