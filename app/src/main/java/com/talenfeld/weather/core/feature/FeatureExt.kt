package com.talenfeld.weather.core.feature

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun <Msg: Any, State: Any, Eff: Any> LifecycleOwner.bindFeature(
    feature: Feature<Msg, State, Eff>,
    stateConsumer: Consumer<State>,
    effectConsumer: Consumer<Eff>
) {
    lifecycle.addObserver(
        object : DefaultLifecycleObserver {

            var disposable: Disposable = EmptyDisposable

            override fun onStart(owner: LifecycleOwner) {
                disposable = feature.subscribe(stateConsumer, effectConsumer)
            }

            override fun onStop(owner: LifecycleOwner) {
                disposable.dispose()
            }
        }
    )
}
