package com.izhxx.simplenewsfeed.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsApiResponse(
    @field:SerializedName("status")
    @Expose
    val status: String,

    @field:SerializedName("articles")
    @Expose
    val articles: List<Articles>
)

data class Articles(
    @field:SerializedName("author")
    @Expose
    val author: String?,

    @field:SerializedName("title")
    @Expose
    val title: String?,

    @field:SerializedName("description")
    @Expose
    val description: String?,

    @field:SerializedName("url")
    @Expose
    val newsUrl: String?,

    @field:SerializedName("urlToImage")
    @Expose
    val imageUrl: String?,

    @field:SerializedName("publishedAt")
    @Expose
    val publishedDate: String?
)