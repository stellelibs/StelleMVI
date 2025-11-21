package com.stelle.libs.mvi

import com.stelle.libs.mvi.event.StelleEvent

/**
 * A pure function responsible for transforming the current [State] into a new [State]
 * based on a given [Event].
 *
 * Implementations of this interface should be free of side effects, such as network requests
 * or database operations.
 *
 * @param State The type of the state to be reduced.
 * @param Event The type of the event that triggers the reduction.
 */
interface StelleReducer<State : StelleState, Event : StelleEvent> {

    /**
     * Takes the `previousState` and an `event` and returns a new `State`.
     *
     * This default implementation returns the `previousState` unmodified, which is useful
     * for reducers that only need to handle a subset of all possible events.
     *
     * @param previousState The state before the event was processed.
     * @param event The event to be processed.
     * @return The new state after applying the event.
     */
    fun reduce(previousState: State, event: Event): State = previousState
}