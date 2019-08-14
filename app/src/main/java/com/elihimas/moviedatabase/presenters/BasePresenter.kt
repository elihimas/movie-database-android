package com.elihimas.moviedatabase.presenters

interface BasePresenter<T> {

    fun attach(view: Any)
}