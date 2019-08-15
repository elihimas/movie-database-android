package com.elihimas.moviedatabase.presenters

import io.reactivex.disposables.Disposable

interface BasePresenter<T> {

    fun attach(view: Any)

    fun addDisposable(disposable: Disposable)

    fun onDestroy()
}