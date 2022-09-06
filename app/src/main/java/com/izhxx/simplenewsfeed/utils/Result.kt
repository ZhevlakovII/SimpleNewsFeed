package com.izhxx.simplenewsfeed.utils

import java.lang.Exception

sealed class Result<out R> {
    data class Successful<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Successful<*> -> "Successful[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}