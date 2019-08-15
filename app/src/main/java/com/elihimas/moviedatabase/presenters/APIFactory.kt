package com.elihimas.moviedatabase.presenters

import com.elihimas.moviedatabase.api.MoviesDatabaseRetrofit
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import okhttp3.logging.HttpLoggingInterceptor


object APIFactory {
    private const val API_KEY_PARAMETER = "api_key"
    private const val LANG_PARAMETER = "language"

    fun createMoviesDatabaseRetrofit(): MoviesDatabaseRetrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val addApiKeyAndLanguageClient = OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalRequestUrl = originalRequest.url()

            val language = Locale.getDefault().toLanguageTag()
            val updatedUrl = originalRequestUrl.newBuilder()
                .addQueryParameter(API_KEY_PARAMETER, MoviesDatabaseRetrofit.API_KEY)
                .addQueryParameter(LANG_PARAMETER, language)
                .build()

            val updatedRequest = originalRequest.newBuilder().url(updatedUrl).build()
            chain.proceed(updatedRequest)
        }.build()

        val loggingClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder().baseUrl(MoviesDatabaseRetrofit.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(addApiKeyAndLanguageClient)
            .client(loggingClient)
            .build().create(MoviesDatabaseRetrofit::class.java)

    }

}