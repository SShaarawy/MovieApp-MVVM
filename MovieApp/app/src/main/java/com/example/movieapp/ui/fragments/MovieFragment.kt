package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.ui.MoviesViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout


class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    private val args: MovieFragmentArgs by navArgs()

    private val sharedViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie

        val collToolbar = requireActivity().findViewById<CollapsingToolbarLayout>(R.id.colltoolbar)
        collToolbar.title = movie.title

        sharedViewModel.getMovieDetails(movie.id)

        binding.webView.apply {
            webViewClient = WebViewClient()

            sharedViewModel.movieDetail.observe(viewLifecycleOwner) {
                println("asd"+it.imdb_id)
                loadUrl("https://www.imdb.com/title/${it.imdb_id}")
            }
        }
        binding.fab.setOnClickListener {
            sharedViewModel.saveMovie(movie)
            Toast.makeText(requireContext(),"Movie is saved",Toast.LENGTH_SHORT).show()
        }
    }
}