package com.elihimas.moviedatabase.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.ui.fragments.MoviesListFragment

class MoviesGenresPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return Genre.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return MoviesListFragment.newInstance(Genre.values()[position])
    }
}