package com.elihimas.moviedatabase.fragments

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun showError(cause: Throwable)

}
