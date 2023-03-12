package com.rex50.converto.utils.extensions

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Double?.toFormattedString(): String {
    if(this == null || this == 0.0)
        return ""

    if(this.toString().contains(".")) {

    }


    return ""
}