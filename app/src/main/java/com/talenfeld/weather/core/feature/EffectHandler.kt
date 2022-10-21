package com.talenfeld.weather.core.feature

import com.talenfeld.weather.core.util.backgroundToUi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface EffectHandler<Effect, Msg>: Disposable {
    fun subscribe(listener: (Msg) -> Unit)
    fun invoke(eff: Effect)
}

abstract class AsyncEffectHandler<Effect, Msg> : EffectHandler<Effect, Msg> {
    private val effDisposables = mutableListOf<Disposable>()
    private var listener: ((Msg) -> Unit)? = null

    override fun dispose() {
        effDisposables.forEach { it.dispose() }
        effDisposables.clear()
        listener = null
    }

    override fun invoke(eff: Effect) {
        listener?.let { invoke(eff, it) }
    }

    override fun subscribe(listener: (Msg) -> Unit) {
        this.listener = listener
    }

    abstract fun invoke(eff: Effect, listener: (Msg) -> Unit): Disposable
}

abstract class SyncEffectHandler<Effect, Msg> : EffectHandler<Effect, Msg> {
    private var listener: ((Msg) -> Unit)? = null

    override fun dispose() {
        listener = null
    }

    override fun invoke(eff: Effect) {
        listener?.let { invoke(eff, it) }
    }

    override fun subscribe(listener: (Msg) -> Unit) {
        this.listener = listener
    }

    abstract fun invoke(eff: Effect, listener: (Msg) -> Unit)
}

fun <Msg : Any, State : Any, Effect : Any> Feature<Msg, State, Effect>.effectHandler(
    effectHandler: EffectHandler<Effect, Msg>,
    initialEffects: Set<Effect> = emptySet()
): Feature<Msg, State, Effect> = EffectWrapper(
    initialEffects = initialEffects,
    innerFeature = this,
    effectHandler = effectHandler
)

private class EffectWrapper<Msg : Any, State : Any, Effect : Any>(
    initialEffects: Set<Effect>,
    private val innerFeature: Feature<Msg, State, Effect>,
    private val effectHandler: EffectHandler<Effect, Msg>,
) : Feature<Msg, State, Effect> by innerFeature {

    init {
        innerFeature.subscribe({}, ::onEffect)
        effectHandler.subscribe(innerFeature::accept)
        initialEffects.forEach(this::onEffect)
    }

    private fun onEffect(eff: Effect) {
        effectHandler.invoke(eff)
    }

    override fun dispose() {
        innerFeature.dispose()
        effectHandler.dispose()
    }
}

fun <Msg: Any> Single<Msg>.toEffectHandlerDisposable(
    listener: (Msg) -> Unit
): Disposable = object : Disposable {

    val delegateDisposable = backgroundToUi().subscribe(listener)

    override fun dispose() {
        delegateDisposable.dispose()
    }
}

fun <Msg: Any> Observable<Msg>.toEffectHandlerDisposable(
    listener: (Msg) -> Unit
): Disposable = object : Disposable {

    val delegateDisposable = backgroundToUi().subscribe(listener)

    override fun dispose() {
        delegateDisposable.dispose()
    }
}

fun <Msg: Any> Flowable<Msg>.toEffectHandlerDisposable(
    listener: (Msg) -> Unit
): Disposable = object : Disposable {

    val delegateDisposable = backgroundToUi().subscribe(listener)

    override fun dispose() {
        delegateDisposable.dispose()
    }
}