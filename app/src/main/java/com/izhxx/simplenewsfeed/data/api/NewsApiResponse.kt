package com.izhxx.simplenewsfeed.data.api

import com.google.gson.annotations.SerializedName

class NewsApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("articles") val articles: List<Articles>
)

data class Articles(
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val newsUrl: String?,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val publishedDate: String?
)