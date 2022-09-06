package com.izhxx.simplenewsfeed.data.repositories

import com.izhxx.simplenewsfeed.data.api.NewsApi
import com.izhxx.simplenewsfeed.data.api.NewsApiResponse
import com.izhxx.simplenewsfeed.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRemoteRepository @Inject constructor(
    private val api: NewsApi
) {
    suspend fun getNews(
        country: String,
        category: String,
        pageSize: Int,
        pageNum: Int
    ): Result<NewsApiResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getNews(country, category, pageSize, pageNum)
            if (response.isSuccessful)
                return@withContext Result.Successful(response.body()!!)
            else
                return@withContext Result.Error(Exception(response.message()))
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }
}