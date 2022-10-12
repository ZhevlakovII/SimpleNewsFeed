package com.izhxx.simplenewsfeed.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.izhxx.simplenewsfeed.data.api.Articles
import com.izhxx.simplenewsfeed.data.api.NewsApi
import com.izhxx.simplenewsfeed.utils.MAX_PAGE_SIZE
import com.izhxx.simplenewsfeed.utils.START_PAGE
import retrofit2.HttpException

class NewsPagingSource(
    private val api: NewsApi
) : PagingSource<Int, Articles>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        val pageNumber: Int = params.key ?: START_PAGE
        val pageSize: Int = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)

        val response = api.getNews(pageSize = pageSize, pageNum = pageNumber)

        return if (response.isSuccessful) {
            val articles = checkNotNull(response.body()).articles
            val nextKey = if (articles.size < pageSize) null else pageNumber + 1
            val prevKey = if (pageNumber == 1) null else pageNumber - 1

            LoadResult.Page(
                data = articles,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } else {
            LoadResult.Error(throwable = HttpException(response))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }

}
