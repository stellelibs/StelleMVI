package com.stelle.libs.mvi

import com.stelle.libs.mvi.event.StelleIntent

/**
 * Defines a component, typically a `ViewModel`, that can process user actions or system events,
 * known as `Intents` in the MVI pattern.
 *
 * An `Intent` represents the user's intention to perform an action (e.g., clicking a button)
 * or a desire to react to a system event.
 */
interface StelleIntentHandler {
    /**
     * The entry point for submitting an [StelleIntent] to be processed.
     * Implementations should define the logic to handle the incoming intent, which may
     * result in state changes or side effects.
     *
     * @param intent The user- or system-initiated action to be handled.
     */
    fun handleIntent(intent: StelleIntent)
}