package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapters.MoviesAdapter
import com.example.movieapp.databinding.FragmentSavedMoviesBinding
import com.example.movieapp.ui.MoviesViewModel
import com.google.android.material.snackbar.Snackbar

class SavedMoviesFragment : Fragment() {
    private lateinit var binding: FragmentSavedMoviesBinding
    private val moviesAdapter = MoviesAdapter()

    private val sharedViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentSavedMoviesBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSavedMovies.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        moviesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
            }
            findNavController().navigate(R.id.action_savedMoviesFragment_to_movieFragment, bundle)
        }

        //Swiping left or right movements
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = moviesAdapter.differ.currentList[position]
                sharedViewModel.deleteMovie(movie)

                Snackbar.make(view, "Successfully deleted movie", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        sharedViewModel.saveMovie(movie)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(binding.rvSavedMovies)

        sharedViewModel.getSavedMovies().observe(viewLifecycleOwner) {
            moviesAdapter.differ.submitList(it)
        }
    }
}