package com.talenfeld.weather.core.di

import com.talenfeld.weather.core.feature.Disposable

class DisposableRef<Ref: Any>(
    private val init: () -> Ref
): Disposable {

    private var ref: Ref? = null

    operator fun invoke(): Ref = (ref ?: init()).also { ref = it }

    override fun dispose() {
        ref = null
    }
}
