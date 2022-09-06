package com.izhxx.simplenewsfeed.data.api

import com.izhxx.simplenewsfeed.data.entities.NewsEntity
import com.izhxx.simplenewsfeed.utils.API_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {
    @Headers("x-api-key: $API_KEY")
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") pageNum: Int
    ): Response<NewsApiResponse>

    companion object {
        fun create(url: String): NewsApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)
        }
    }
}