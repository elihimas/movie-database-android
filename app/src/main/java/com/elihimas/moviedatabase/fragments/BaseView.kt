package com.elihimas.moviedatabase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.elihimas.moviedatabase.presenters.BasePresenter

abstract class BaseView<T : BasePresenter<*>> : Fragment() {

    var presenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDagger()

        presenter = createPresenter()
        presenter?.attach(this)
    }

    abstract fun injectDagger()

    abstract fun createPresenter(): T

}