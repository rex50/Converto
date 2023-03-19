package com.rex50.converto.utils

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Response<T> {
    val response = try {
        call()
    } catch (e: Exception) {
        Response.error<T>(400, ResponseBody.create(MediaType.get("text/text"), e.cause?.message ?: "Problem while getting latest data"))
    }
    return response
}