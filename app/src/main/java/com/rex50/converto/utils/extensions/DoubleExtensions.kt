package com.rex50.converto.utils.extensions

fun Double?.orZero(): Double {
    return this ?: 0.0
}