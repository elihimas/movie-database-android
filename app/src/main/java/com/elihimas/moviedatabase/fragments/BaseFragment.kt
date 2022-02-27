package com.elihimas.moviedatabase.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.elihimas.moviedatabase.R

abstract class BaseFragment : Fragment(), BaseView {

    override fun showError(cause: Throwable) {
        requireActivity().runOnUiThread {
            hideLoading()
            Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_LONG).show()
        }
    }
}