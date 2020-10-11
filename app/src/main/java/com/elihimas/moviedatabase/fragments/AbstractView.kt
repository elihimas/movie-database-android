package com.elihimas.moviedatabase.fragments

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.presenters.BasePresenter

abstract class AbstractView<PresenterType : BasePresenter<*>> : Fragment(), BaseView {

    var presenter: PresenterType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = createPresenter()
        presenter?.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.onDestroy()
    }

    override fun showError(cause: Throwable) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_LONG).show()
        }
    }

    abstract fun createPresenter(): PresenterType

}