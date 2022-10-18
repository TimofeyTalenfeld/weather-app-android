package com.talenfeld.weather.core.feature

interface Feature<Msg : Any, State : Any, Effect : Any> : Disposable {
    val currentState: State
    fun subscribe(stateConsumer: (state: State) -> Unit, effectConsumer: (eff: Effect) -> Unit): Disposable
    fun accept(msg: Msg)
}
