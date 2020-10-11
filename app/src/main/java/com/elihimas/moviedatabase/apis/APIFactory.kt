package com.elihimas.moviedatabase.apis

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

    fun createMoviesDatabaseService(): MoviesDatabaseService {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val language = Locale.getDefault().toLanguageTag()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalRequestUrl = originalRequest.url()

                val updatedUrl = originalRequestUrl.newBuilder()
                    .addQueryParameter(API_KEY_PARAMETER, MoviesDatabaseService.API_KEY)
                    .addQueryParameter(LANG_PARAMETER, language)
                    .build()

                val updatedRequest = originalRequest.newBuilder().url(updatedUrl).build()
                chain.proceed(updatedRequest)
            }
            .build()

        return Retrofit.Builder().baseUrl(MoviesDatabaseService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build().create(MoviesDatabaseService::class.java)

    }

}