package com.rex50.converto.utils.extensions

import retrofit2.Response
import com.rex50.converto.utils.Result

fun <Type: Any, ReturnType: Any> Response<Type>.mapSafelyToResult(transform: (Type) -> ReturnType): Result<ReturnType> {
    return if(isSuccessful && body() != null) {
        try {
            Result.Success(transform(body()!!))
        }catch (e: Exception) {
            Result.Failure(e)
        }
    } else {
        // TODO: use errorBody for proper message
        Result.Failure(Exception("Problem while connecting to server"))
    }
}

suspend fun <Type: Any, ReturnType: Any> Result<Type>.mapSafelyIfSuccess(transform: suspend (Type) -> ReturnType): Result<ReturnType> {
    return when(this) {
        is Result.Success -> try {
            Result.Success(transform(this.data))
        } catch (e: Exception) {
            Result.Failure(e)
        }

        is Result.Failure -> this
    }
}