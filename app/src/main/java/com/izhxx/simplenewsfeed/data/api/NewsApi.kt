package com.izhxx.simplenewsfeed.data.api


import com.izhxx.simplenewsfeed.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import androidx.annotation.IntRange
import com.izhxx.simplenewsfeed.utils.*
import retrofit2.Response

interface NewsApi {
    /**
     * @param country The 2-letter ISO 3166-1 code of the country you want to get articles for.
     * @param category The category you want to get articles for.
     * @param pageSize The number of results to return per page (request). 20 is the default, 100 is the maximum.
     * @param pageNum Use this to page through the results if the total results found is greater than the page size.
     *
     * [Api documentation](https://newsapi.org/docs/endpoints/top-headlines)
     **/
    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("category") category: String = DEFAULT_CATEGORY,
        @Query("pageSize") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("page") @IntRange(from = 1) pageNum: Int = START_PAGE
    ): Response<NewsApiResponse>

    companion object {
        fun create(url: String): NewsApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

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