package com.izhxx.simplenewsfeed.di

import com.izhxx.simplenewsfeed.BuildConfig
import com.izhxx.simplenewsfeed.data.api.NewsApi
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
        return NewsApi.create(BuildConfig.BASE_URL)
    }
}