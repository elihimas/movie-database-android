package com.elihimas.moviedatabase.presenters

import com.elihimas.moviedatabase.api.MoviesDatabaseRetrofit
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


object APIFactory {
    fun createMoviesDatabaseRetrofit(): MoviesDatabaseRetrofit? {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val addCredentialsClient = OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalRequestUrl = originalRequest.url()

            val updatedUrl = originalRequestUrl.newBuilder()
                .addQueryParameter("api_key", MoviesDatabaseRetrofit.API_KEY)
                .build()

            val updatedRequest = originalRequest.newBuilder().url(updatedUrl).build()
            chain.proceed(updatedRequest)
        }.build()

        return Retrofit.Builder().baseUrl(MoviesDatabaseRetrofit.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(addCredentialsClient)
            .build().create(MoviesDatabaseRetrofit::class.java)

    }

}