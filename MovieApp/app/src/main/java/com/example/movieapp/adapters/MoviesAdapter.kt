package com.example.movieapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.model.Result


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private lateinit var tvTitle: TextView
    private lateinit var tvPublishedAt: TextView
    private lateinit var tvDescription: TextView

    companion object DiffCallBack : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, DiffCallBack)

    class MoviesViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]

        holder.apply {

            Glide.with(itemView)
                .load("http://image.tmdb.org/t/p/w500/"+ movie.poster_path)
                .transform(RoundedCorners(90))
                .into(binding.ivMovieImage)

            binding.tvVote.text = itemView.resources.getString(R.string.vote,movie.vote_average.toString())

            val layout = LayoutInflater.from(itemView.context).inflate(R.layout.item_dialog, null)

            val alertDialog = AlertDialog.Builder(itemView.context)
                .setView(layout)
                .create()

            tvTitle = layout.findViewById(R.id.tvTitle)
            tvPublishedAt = layout.findViewById(R.id.tvPublishedAt)
            tvDescription = layout.findViewById(R.id.tvDescription)

            tvTitle.text = movie.title
            tvPublishedAt.text = movie.release_date
            tvDescription.text = movie.overview

            binding.btnInfo.setOnClickListener {
                alertDialog.show()
            }
            itemView.setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }

        }
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}