package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapters.MoviesAdapter
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.ui.MoviesViewModel

class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding
    private val moviesAdapter = MoviesAdapter()

    private val sharedViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentMovieListBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovieList.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        sharedViewModel.movieList.observe(viewLifecycleOwner) {
            moviesAdapter.differ.submitList(it.results)
        }

        moviesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
            }
            findNavController().navigate(R.id.action_movieListFragment_to_movieFragment, bundle)
        }

    }
}