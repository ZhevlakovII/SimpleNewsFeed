package com.izhxx.simplenewsfeed.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.izhxx.simplenewsfeed.data.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert
    suspend fun saveNews(news: NewsEntity)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("DELETE FROM news")
    suspend fun clearAllNews()
}