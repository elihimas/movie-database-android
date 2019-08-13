package com.elihimas.moviedatabase.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.elihimas.moviedatabase.extensions.titleResId
import com.elihimas.moviedatabase.model.Genres

class MoviesGenresPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return MovieGenreFragment.newInstance(Genres.values()[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(Genres.values()[position].titleResId)
    }

    override fun getCount(): Int {
        return Genres.values().size
    }
}