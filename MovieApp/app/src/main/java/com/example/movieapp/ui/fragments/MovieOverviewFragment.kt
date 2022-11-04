package com.example.movieapp.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
    ): View? {
        binding = FragmentMovieOverviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie

        val collToolbar = requireActivity().findViewById<CollapsingToolbarLayout>(R.id.colltoolbar)
        collToolbar.title = "Overview"

        val bgCollToolbar = requireActivity().findViewById<ImageView>(R.id.bgCollToolbar)
        Glide.with(requireActivity())
            .load("http://image.tmdb.org/t/p/w500/"+ movie.poster_path)
            .into(bgCollToolbar)

        binding.tvTitle.text = movie.title
        binding.tvDate.text = "(${movie.release_date.substring(0, 4)})"
        binding.tvOverview.text = movie.overview

        binding.rvMovieGenres.apply {
            adapter = genresAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        val list = movie.genre_ids
        val list2 = sharedViewModel.genres.value?.genres
        val list3 = mutableListOf<Genre>()

        for (int in list) {
            for(item in list2!!){
                if(int == item.id){
                    list3.add(item)
                }
            }
        }

        println("size"+list3.size)

        genresAdapter.differ.submitList(list3)

    }
}