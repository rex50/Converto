package com.rex50.converto.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Response<T> {
    val response = try {
        call()
    } catch (e: Exception) {
        Response.error(400,
            (e.cause?.message
                ?: "Problem while getting latest data").toResponseBody("text/text".toMediaType())
        )
    }
    return response
}