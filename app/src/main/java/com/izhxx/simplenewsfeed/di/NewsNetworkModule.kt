package com.izhxx.simplenewsfeed.di

import com.izhxx.simplenewsfeed.data.api.NewsApi
import com.izhxx.simplenewsfeed.utils.BASE_NEWS_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsNetworkModule {
    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi {
        return NewsApi.create(BASE_NEWS_URL)
    }
}