package com.elihimas.moviedatabase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elihimas.moviedatabase.activities.MovieDetailsActivity
import com.elihimas.moviedatabase.databinding.MovieItemBinding
import com.elihimas.moviedatabase.model.Movie


class MoviesAdapter : PagedListAdapter<Movie, MovieViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener {
            val movie = it.tag as Movie
            MovieDetailsActivity.start(it.context, movie.id, movie.title)
        }

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
        }
    }
}

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private companion object {
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w342/"
    }

    fun bind(movie: Movie) {
        binding.movieTitleText.text = movie.title

        val imageUrl = IMAGES_URL + movie.posterPath
        Glide.with(binding.root.context)
            .load(imageUrl)
            .centerCrop()
            .into(binding.posterImage)
        binding.root.tag = movie
    }

}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.title == oldItem.title
}