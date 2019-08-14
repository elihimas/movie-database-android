package com.elihimas.moviedatabase.presenters

abstract class AbstractPresenter<T> : BasePresenter<T> {
    var view: T? = null

    override fun attach(view: Any) {
        this.view = view as T
    }
}