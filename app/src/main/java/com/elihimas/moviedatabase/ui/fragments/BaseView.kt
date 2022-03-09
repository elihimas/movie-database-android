package com.elihimas.moviedatabase.ui.fragments

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun showError(cause: Throwable)

}
