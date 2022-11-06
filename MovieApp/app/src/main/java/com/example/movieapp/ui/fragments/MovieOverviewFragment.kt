package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapters.GenresAdapter
import com.example.movieapp.databinding.FragmentMovieOverviewBinding
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Result
import com.example.movieapp.ui.MoviesViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout


class MovieOverviewFragment : Fragment() {
    private lateinit var binding: FragmentMovieOverviewBinding
    private val genresAdapter = GenresAdapter()
    private val args: MovieFragmentArgs by navArgs()

    private var isExists: Int = 0

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

        setCollapsingToolbar(movie.poster_path)
        setMovieGenresRecyclerView(movie.genre_ids)

        sharedViewModel.isExists(movie.id).observe(viewLifecycleOwner) {
            checkIsExists(it)
            isExists = it
        }

        binding.tvTitle.text = movie.title
        binding.tvDate.text = "(${movie.release_date.substring(0, 4)})   " + movie.vote_average
        binding.tvOverview.text = movie.overview
        binding.ratingBar.rating = (movie.vote_average / 2).toFloat()

        binding.btnSite.setOnClickListener { navigateToSite(movie) }
        binding.ivIsSaved.setOnClickListener { saveDeleteMovie(isExists, movie) }

    }

    private fun navigateToSite(movie: Result) {
        val bundle = Bundle().apply {
            putSerializable("movie", movie)
        }
        findNavController().navigate(R.id.action_movieOverviewFragment_to_movieFragment, bundle)
    }

    private fun checkIsExists(isExists: Int): Boolean {
        return if (isExists > 0) {
            binding.ivIsSaved.setImageResource(R.drawable.ic_saved)
            true
        }  else  {
            binding.ivIsSaved.setImageResource(R.drawable.ic_save)
            false
        }
    }

    private fun saveDeleteMovie(isExists: Int, movie: Result) {
        if (isExists > 0) {
            sharedViewModel.deleteMovie(movie)
            binding.ivIsSaved.setImageResource(R.drawable.ic_save)
            Toast.makeText(requireActivity(), "Deleted from records", Toast.LENGTH_SHORT).show()
        } else {
            sharedViewModel.saveMovie(movie)
            binding.ivIsSaved.setImageResource(R.drawable.ic_saved)
            Toast.makeText(requireActivity(), "Movie is saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCollapsingToolbar(posterPath: String?) {
        val collToolbar = requireActivity().findViewById<CollapsingToolbarLayout>(R.id.colltoolbar)
        collToolbar.title = " "

        val bgCollToolbar = requireActivity().findViewById<ImageView>(R.id.bgCollToolbar)
        Glide.with(requireActivity())
            .load("http://image.tmdb.org/t/p/w500/$posterPath")
            .into(bgCollToolbar)
    }

    private fun setMovieGenresRecyclerView(genreIds: List<Int>) {
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
        genresAdapter.differ.submitList(idList)

        binding.rvMovieGenres.apply {
            adapter = genresAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}