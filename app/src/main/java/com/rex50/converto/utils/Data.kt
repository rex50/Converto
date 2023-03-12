package com.rex50.converto.utils

import java.lang.reflect.Type

//data class Data<RequestData>(var responseType: Status, var data: RequestData? = null, var error: Exception? = null)
sealed class Data<out Type: Any> {
    class Successful<out Type : Any>(val data: Type): Data<Type>()
    class Error(val message: String): Data<Nothing>()
    object Loading: Data<Nothing>()
}

enum class Status {
    SUCCESSFUL, ERROR, LOADING
}