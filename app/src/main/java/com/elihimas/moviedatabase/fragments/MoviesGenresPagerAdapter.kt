package com.elihimas.moviedatabase.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.elihimas.moviedatabase.model.Genre

class MoviesGenresPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return MoviesGenreFragment.newInstance(Genre.values()[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(
            Genre.values()[position].getTitleResId()
        )
    }

    override fun getCount(): Int {
        return Genre.values().size
    }
}