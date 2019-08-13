package com.elihimas.moviedatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.model.Genres

class MovieGenreFragment : Fragment() {

    companion object {
        private const val ARG_GENRE = "genre"

        @JvmStatic
        fun newInstance(genre: Genres): MovieGenreFragment {
            return MovieGenreFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_GENRE, genre)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val genre = arguments?.getSerializable(ARG_GENRE) as Genres
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_genre, container, false)
        return view
    }
}