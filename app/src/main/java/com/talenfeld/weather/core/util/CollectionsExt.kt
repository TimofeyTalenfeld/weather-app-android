package com.talenfeld.weather.core.util

fun <T> Iterable<T>.indexOrNull(predicate: (T) -> Boolean): Int? =
    when (val index = indexOfFirst(predicate)) {
        -1 -> null
        else -> index
    }
