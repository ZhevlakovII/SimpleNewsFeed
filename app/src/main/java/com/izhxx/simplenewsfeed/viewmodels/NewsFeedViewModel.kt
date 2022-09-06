package com.izhxx.simplenewsfeed.viewmodels

import androidx.lifecycle.*
import com.izhxx.simplenewsfeed.data.entities.NewsEntity
import com.izhxx.simplenewsfeed.data.repositories.NewsLocalRepository
import com.izhxx.simplenewsfeed.data.repositories.NewsRemoteRepository
import com.izhxx.simplenewsfeed.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsLocalRepo: NewsLocalRepository,
    private val newsRemoteRepo: NewsRemoteRepository
) : ViewModel() {
    private val _loadingStatus = MutableLiveData(true)
    val loadingStatus: LiveData<Boolean> = _loadingStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _news = MutableLiveData<List<NewsEntity>>()
    val news = _news

    private val _remoteNews = arrayListOf<NewsEntity>()

    fun getNews(
        country: String,
        category: String,
        pageSize: Int,
        pageNum: Int
    ) {
        viewModelScope.launch {
            _loadingStatus.postValue(true)
            val _localNews = newsLocalRepo.getAllNews()

            when (val newsResult = newsRemoteRepo.getNews(country, category, pageSize, pageNum)) {
                is Result.Successful -> {
                    newsLocalRepo.clearAllNews()
                    _remoteNews.clear()

                    val listOfNews = newsResult.data
                    var id = 1

                    if ((news.value?.size ?: 1) > 0)
                        id = news.value?.lastIndex ?: 1

                    listOfNews.articles.forEach { articles ->
                        val entity= NewsEntity(
                            id,
                            nullCheck(articles.author),
                            nullCheck(articles.title),
                            nullCheck(articles.description),
                            nullCheck(articles.newsUrl),
                            nullCheck(articles.imageUrl),
                            nullCheck(articles.publishedDate)
                        )
                        _remoteNews.add(entity)
                        id++
                    }

                    _remoteNews.forEach {
                        newsLocalRepo.saveNews(it)
                    }
                    news.value = _remoteNews
                    _loadingStatus.postValue(false)
                }

                is Result.Error -> {
                    _loadingStatus.postValue(false)
                    _localNews.collect { newsList ->
                        if (newsList.isEmpty())
                            news.value = emptyList()
                        else
                            news.value = newsList
                    }
                    _error.postValue(newsResult.exception.message)
                }
            }
        }
    }

    private fun nullCheck(string: String?): String {
        return string ?: ""
    }
}