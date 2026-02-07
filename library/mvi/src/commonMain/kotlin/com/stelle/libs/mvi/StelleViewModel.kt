package com.stelle.libs.mvi

import androidx.lifecycle.viewModelScope
import com.stelle.libs.mvi.event.StelleEvent

/**
 * The core component of the MVI architecture, acting as a bridge between the UI and business logic.
 * It processes intents, manages state, and emits side effects.
 *
 * This ViewModel integrates with `androidx.lifecycle.ViewModel` and uses `viewModelScope` for coroutines.
 *
 * @param State The type of the state managed by this ViewModel, which must extend [StelleState].
 * @param Event The type of the events that this ViewModel can process, which must extend [StelleEvent].
 * @param data The [StelleData] instance that holds the state and effect flows.
 * @param reducer The [StelleReducer] responsible for state transformations based on events. Defaults to a no-op reducer if not provided.
 */
abstract class StelleViewModel<State : StelleState, Event : StelleEvent>(
    override val data: StelleData<State>,
    reducer: StelleReducer<State, Event> = object : StelleReducer<State, Event> {}
) :
    StelleParentViewModel<State, Event>(reducer = reducer), StelleIntentHandler {

    /**
     * A convenience constructor that creates a default [StelleData] implementation.
     *
     * Use this constructor when you don't need a custom [StelleData] implementation and just want
     * to provide the initial state.
     *
     * @param initialState The initial state of the ViewModel.
     * @param reducer The [StelleReducer] for this ViewModel's specific events. Defaults to a no-op reducer if not provided.
     */
    constructor(
        initialState: State,
        reducer: StelleReducer<State, Event> = object : StelleReducer<State, Event> {}
    ) : this(
        object : StelleData<State>(initialState) {},
        reducer
    )

    /**
     * A [StateFlow] representing the current state of the UI.
     * The UI should observe this flow to react to state changes and re-render itself.
     */
    val state = data.state

    init {
        viewModelScope
    }

    /**
     * A [Flow] for emitting one-time side effects that the UI should handle (e.g., navigation, toasts).
     */
    val effect = data.effect
}