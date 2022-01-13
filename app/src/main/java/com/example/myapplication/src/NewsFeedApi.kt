package com.example.myapplication.src

import android.util.Log
import com.example.myapplication.model.Response
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedApi {

    @GET("/search")
    suspend fun getNewsList(
        @Query("page") page: Int,
        @Query("api-key") apiKey: String? = null,
    ): Response

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
        fun create(): NewsFeedApi {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL.toHttpUrlOrNull()!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsFeedApi::class.java)
        }
    }
}