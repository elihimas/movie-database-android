package com.elihimas.moviedatabase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.activities.MovieDetailsActivity
import com.elihimas.moviedatabase.model.Movie
import kotlinx.android.synthetic.main.movie_item.view.*


class MoviesAdapter : PagedListAdapter<Movie, MovieViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        view.setOnClickListener {
            val movie = view.tag as Movie
            MovieDetailsActivity.start(view.context, movie.id, movie.title)
        }

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
        }
    }
}

class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private companion object {
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w342/"
    }

    fun bind(movie: Movie) {
        view.movie_title_text.text = movie.title

        val imageUrl = IMAGES_URL + movie.posterPath
        Glide.with(view.context)
            .load(imageUrl)
            .centerCrop()
            .into(view.posterImage)
        view.tag = movie
    }

}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.title == oldItem.title
}