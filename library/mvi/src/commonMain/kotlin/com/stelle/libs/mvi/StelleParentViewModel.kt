package com.stelle.libs.mvi

import androidx.lifecycle.ViewModel
import com.stelle.libs.mvi.event.StelleEffect
import com.stelle.libs.mvi.event.StelleEvent
import com.stelle.libs.mvi.event.StelleIntent

/**
 * A ViewModel that can act as a parent to a collection of [StelleChildViewModel] instances.
 * It is responsible for managing a shared state and orchestrating its children.
 *
 * @param State The type of the state shared across the parent and its children.
 * @param Event The type of events this parent ViewModel can process directly.
 * @property reducer The [StelleReducer] for this parent's specific events.
 */
abstract class StelleParentViewModel<State : StelleState, Event : StelleEvent>(
    private val reducer: StelleReducer<State, Event>
) : ViewModel(), StelleIntentHandler {
    internal abstract val data: StelleData<State>

    /**
     * A container holding the list of child ViewModels.
     * Subclasses should override this property to provide their specific child ViewModel instances.
     */
    protected open val children: StelleChildViewModelContainer<State>? = null

    init {
        doChildren { it.setParent(this) }
    }

    /**
     * Establishes the parent-child relationship by setting this ViewModel as the parent for all its children.
     * This is called automatically during initialization.
     */
    protected fun setParents() {
        doChildren { it.setParent(this) }
    }

    /**
     * Executes a given block of code on each registered child ViewModel.
     *
     * @param block The action to be performed on each [StelleChildViewModel].
     */
    protected fun doChildren(block: (StelleChildViewModel<State, *>) -> Unit) {
        children?.childrenViewModels?.forEach { block(it) }
    }

    /**
     * Overridden to automatically propagate the `onCleared` event to all child ViewModels,
     * ensuring proper resource cleanup.
     */
    override fun onCleared() {
        super.onCleared()
        doChildren { it.onCleared() }
    }

    /**
     * Delegates the handling of the incoming [StelleIntent] to all its child ViewModels.
     * and must be overrided to manage the intents
     */
    override fun handleIntent(intent: StelleIntent) {
        handleIntentChildren(intent)
    }

    private fun handleIntentChildren(intent: StelleIntent) = doChildren { it.handleIntent(intent) }

    /**
     * Sends a side effect to be consumed by the UI.
     * This is the primary way for ViewModels to trigger one-time actions like navigation or toasts.
     *
     * @param effect The [StelleEffect] to be sent.
     */
    protected open fun sendEffect(effect: StelleEffect) {
        data.sendEffect(effect)
    }

    /**
     * Sends an event to be processed by the reducer.
     * The reducer will then generate a new state based on this event.
     *
     * @param event The [StelleEvent] to be processed.
     */
    protected open fun sendEvent(event: Event) {
        data.updateState(reducer.reduce(data.state.value, event))
    }
}