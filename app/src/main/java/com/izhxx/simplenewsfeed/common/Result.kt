package com.izhxx.simplenewsfeed.common

import java.lang.Exception

sealed class Result {
    data class Successful<out T>(val data: T) : Result()
    data class Error(val exception: Exception) : Result()
}