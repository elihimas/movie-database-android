package com.elihimas.moviedatabase.presenters

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class AbstractPresenter<T> : BasePresenter<T> {

    var view: T? = null

    private val compositeDisposable = CompositeDisposable()

    override fun attach(view: Any) {
        this.view = view as T
    }

    override fun addDisable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun destroy() {
        compositeDisposable.dispose()
    }


}