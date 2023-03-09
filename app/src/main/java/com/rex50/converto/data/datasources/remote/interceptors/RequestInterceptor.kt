package com.rex50.converto.data.datasources.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val requestBuilder = request.newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        val response = chain.proceed(requestBuilder)

        return response
    }

}