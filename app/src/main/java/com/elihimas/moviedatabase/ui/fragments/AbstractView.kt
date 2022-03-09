package com.elihimas.moviedatabase.ui.fragments

import android.os.Bundle
import com.elihimas.moviedatabase.presenters.BasePresenter

abstract class AbstractView<PresenterType : BasePresenter<*>> : BaseFragment() {

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

    abstract fun createPresenter(): PresenterType

}