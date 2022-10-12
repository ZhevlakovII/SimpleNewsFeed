package com.izhxx.simplenewsfeed.presenter.news

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.izhxx.simplenewsfeed.data.repositories.NewsRemoteRepository
import com.izhxx.simplenewsfeed.data.api.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    newsRemoteRepo: NewsRemoteRepository
) : ViewModel() {
    val news: StateFlow<PagingData<Articles>> = newsRemoteRepo
        .getNews()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}