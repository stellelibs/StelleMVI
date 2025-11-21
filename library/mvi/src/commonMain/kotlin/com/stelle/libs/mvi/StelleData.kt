package com.stelle.libs.mvi

import com.stelle.libs.mvi.event.StelleEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * A base class responsible for managing the state and side effects within the MVI architecture.
 * It holds the current state and provides mechanisms to update it and to send one-time effects.
 *
 * @param State The type of the state, which must extend [StelleState].
 * @param initialState The initial state of the component.
 */
abstract class StelleData<State : StelleState>(
    initialState: State,
) {

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)


    internal val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val _effects = Channel<StelleEffect>(capacity = Channel.UNLIMITED)


    internal val effect = _effects.receiveAsFlow()


    internal fun updateState(block: (State) -> State) {
        _state.value = block(state.value)
    }


    internal fun updateState(newState: State) {
        _state.value = newState
    }


    internal fun sendEffect(effect: StelleEffect) {
        _effects.trySend(effect)
    }
}