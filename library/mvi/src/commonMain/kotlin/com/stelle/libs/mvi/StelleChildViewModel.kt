package com.stelle.libs.mvi

import com.stelle.libs.mvi.event.StelleEffect
import com.stelle.libs.mvi.event.StelleEvent

/**
 * A ViewModel that operates as a child of a [StelleParentViewModel].
 * It shares the same [StelleState] but can handle its own logic and events independently.
 * This is useful for breaking down complex screens into smaller, more manageable components.
 *
 * @param State The type of the shared state, which must extend [StelleState].
 * @param Event The type of events this child ViewModel can process.
 * @param reducer The [StelleReducer] for this child's specific events. Defaults to a no-op reducer if not provided.
 */
abstract class StelleChildViewModel<State : StelleState, Event : StelleEvent>(
    reducer: StelleReducer<State, Event> = object : StelleReducer<State, Event> {}
) : StelleParentViewModel<State, Event>(reducer) {
    private var parent: StelleParentViewModel<State, *>? = null

    private val delayedEffects = mutableListOf<StelleEffect>()
    private val delayedEvents = mutableListOf<Event>()

    override val data
        get() =
            parent?.data
                ?: throw IllegalStateException("Parent ViewModel is not set for ${this::class.simpleName}. Ensure the parent is initialized.")

    /**
     * A lifecycle method called when the parent ViewModel has been successfully attached.
     * This is the ideal place to perform initialization logic that depends on the parent,
     * such as dispatching initial events.
     */
    protected open fun ignitedParent() {
        sendDelayedEffects()
        sendDelayedEvents()
    }

    private fun sendDelayedEvents() {
        delayedEvents.forEach { super.sendEvent(it) }
        delayedEvents.clear()
    }

    private fun sendDelayedEffects() {
        delayedEffects.forEach { super.sendEffect(it) }
        delayedEffects.clear()
    }

    internal fun setParent(parentViewModel: StelleParentViewModel<State, *>) {
        this.parent = parentViewModel
        ignitedParent()
    }

    /**
     * Sends an event to be processed. If the parent is not yet available,
     * the event is queued and will be dispatched once the parent is attached.
     */
    override fun sendEvent(event: Event) {
        if (parent == null) {
            delayedEvents.add(event)
            return
        }
        super.sendEvent(event)
    }

    /**
     * Sends a side effect to be consumed by the UI. If the parent is not yet available,
     * the effect is queued and will be dispatched once the parent is attached.
     */
    override fun sendEffect(effect: StelleEffect) {
        if (parent == null) {
            delayedEffects.add(effect)
            return
        }
        super.sendEffect(effect)
    }
}