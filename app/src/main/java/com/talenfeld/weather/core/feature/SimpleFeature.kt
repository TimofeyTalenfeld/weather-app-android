package com.talenfeld.weather.core.feature

import kotlin.properties.Delegates

typealias Consumer<T> = (value: T) -> Unit
typealias Reducer<Msg, State, Effect> = (msg: Msg, state: State) -> Pair<State, Set<Effect>>

@Suppress("RestrictedApi")
class SimpleFeature<Msg : Any, State : Any, Effect : Any>(
    initialState: State,
    private val reducer: Reducer<Msg, State, Effect>
) : Feature<Msg, State, Effect> {

    private val stateConsumers: MutableMap<Int, Consumer<State>> = LinkedHashMap()
    private val effectConsumers: MutableMap<Int, Consumer<Effect>> = LinkedHashMap()

    // these counters used to guarantee ascending order of iteration through collections
    private var stateKeyCounter: Int = 0
    private var effectKeyCounter: Int = 0

    private var isDisposed = false

    override var currentState: State by Delegates.observable(initialState) { _, _, newState ->
        stateConsumers.forEach { (_, consumer) -> consumer(newState) }
    }
        private set

    override fun subscribe(stateConsumer: Consumer<State>, effectConsumer: Consumer<Effect>): Disposable {
        if (isDisposed) return EmptyDisposable
        val stateKey = stateKeyCounter++
        val effectKey = effectKeyCounter++
        stateConsumer(currentState)
        stateConsumers[stateKey] = stateConsumer
        effectConsumers[effectKey] = effectConsumer
        return object : Disposable {
            override fun dispose() {
                stateConsumers.remove(stateKey)
                effectConsumers.remove(effectKey)
            }
        }
    }

    override fun accept(msg: Msg) {
        if (isDisposed) return
        val (state, effects) = reducer(msg, currentState)
        currentState = state
        effects.forEach { effect ->
            effectConsumers.toList().forEach { (_, consumer) -> consumer(effect) }
        }
    }

    override fun dispose() {
        isDisposed = true
    }
}
