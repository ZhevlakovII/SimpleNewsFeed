package com.izhxx.simplenewsfeed.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.izhxx.simplenewsfeed.data.api.NewsApi
import com.izhxx.simplenewsfeed.data.api.Articles
import com.izhxx.simplenewsfeed.utils.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRemoteRepository @Inject constructor(
    private val api: NewsApi
) {
    fun getNews(): Flow<PagingData<Articles>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(api) }
        ).flow
    }
}
