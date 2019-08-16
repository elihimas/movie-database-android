package com.elihimas.moviedatabase.apis

interface LoadItemsCallbacks {

    fun onNothingFound()
    fun onError(cause: Throwable)
}