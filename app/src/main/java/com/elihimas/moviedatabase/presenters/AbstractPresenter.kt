package com.elihimas.moviedatabase.presenters

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class AbstractPresenter<ViewType> : BasePresenter<ViewType> {

    var view: ViewType? = null

    private val compositeDisposable = CompositeDisposable()

    override fun attach(view: Any) {
        this.view = view as ViewType
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }


}