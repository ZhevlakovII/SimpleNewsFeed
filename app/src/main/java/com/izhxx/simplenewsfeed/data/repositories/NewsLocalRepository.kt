package com.izhxx.simplenewsfeed.data.repositories

import com.izhxx.simplenewsfeed.data.database.NewsDao
import com.izhxx.simplenewsfeed.data.entities.NewsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsLocalRepository @Inject constructor(
    private val newsDao: NewsDao
) {
    suspend fun saveNews(news: NewsEntity) = newsDao.saveNews(news)

    fun getAllNews() = newsDao.getAllNews()

    suspend fun clearAllNews() = newsDao.clearAllNews()
}