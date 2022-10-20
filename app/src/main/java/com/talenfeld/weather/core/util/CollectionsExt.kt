package com.talenfeld.weather.core.util

inline fun <T> Iterable<T>.indexOrNull(predicate: (T) -> Boolean): Int? =
    when (val index = indexOfFirst(predicate)) {
        -1 -> null
        else -> index
    }

inline fun <T, V> zip(vararg lists: List<T>, transform: (List<T>) -> V): List<V> {
    val minSize = lists.map(List<T>::size).min() ?: return emptyList()
    val list = ArrayList<V>(minSize)

    val iterators = lists.map { it.iterator() }
    var i = 0
    while (i < minSize) {
        list.add(transform(iterators.map { it.next() }))
        i++
    }

    return list
}