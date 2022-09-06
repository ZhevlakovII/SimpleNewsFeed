package com.izhxx.simplenewsfeed.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class NewsEntity (
        @PrimaryKey
        val id: Int,

        val author: String,
        val title: String,
        val description: String,
        val newsUrl: String,
        val imageUrl: String,
        val publishedDate: String
)