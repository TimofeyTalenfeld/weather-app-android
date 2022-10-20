package com.talenfeld.weather.core.util

import android.view.View

inline fun <V: View, T: Any> V.applyOrInvisible(property: T?, action: V.(T) -> Unit) {
    when (property) {
        null -> visibility = View.INVISIBLE
        else -> {
            visibility = View.VISIBLE
            action(property)
        }
    }
}
