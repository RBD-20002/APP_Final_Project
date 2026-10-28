package com.example.finalproject.data.remote.interceptor

import okhttp3.logging.HttpLoggingInterceptor

object NetworkLogginInterceptor {

    fun create(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}