package com.stelle.libs.mvi.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.stelle.libs.mvi.StelleState
import com.stelle.libs.mvi.StelleViewModel
import com.stelle.libs.mvi.event.StelleEffect
import com.stelle.libs.mvi.event.StelleIntent
import kotlinx.coroutines.flow.Flow

/**
 * A base class for creating a screen in Compose that follows the MVI pattern.
 * It connects a [StelleViewModel] to a composable UI, handling state and effect streams.
 *
 * @param S The type of the [StelleState] that this screen observes.
 * @param V The type of the [StelleViewModel] that this screen uses.
 * @param onEffectFromParent A callback to propagate side effects to a parent component, if any.
 */
abstract class StelleScreen<S : StelleState, V : StelleViewModel<S, *>>(val onEffectFromParent: ((effect: StelleEffect) -> Unit)? = null) {
    /** The ViewModel instance associated with this screen, available to subclasses. */
    protected lateinit var viewModel: V
    private val delayedIntent = mutableListOf<StelleIntent>()

    /**
     * The main entry point for rendering the screen.
     * It orchestrates ViewModel creation, state collection, and effect handling before calling [Content].
     */
    @Composable
    open fun Show() {
        viewModel = createViewModel()
        sendDelayedIntents()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effects = rememberFlowWithLifecycle(viewModel.effect)
        Content(state, ::sendIntent)
        LaunchedEffect(effects) {
            effects.collect { effect ->
                onEffect(effect)
                onEffectFromParent?.invoke(effect)
            }
        }
    }

    /**
     * A lifecycle hook for subclasses to handle side effects from the ViewModel.
     * Override this method to react to specific [StelleEffect] instances.
     *
     * @param effect The side effect emitted by the ViewModel.
     */
    protected open fun onEffect(effect: StelleEffect) = Unit

    /**
     * Defines the actual UI content of the screen.
     * Subclasses must implement this to render the UI based on the given [state].
     *
     * @param state The current state to be rendered.
     * @param onIntents A callback to send a [StelleIntent] to the ViewModel for processing.
     */
    @Composable
    abstract fun Content(
        state: S,
        onIntents: (intent: StelleIntent) -> Unit
    )

    /**
     * A factory method for creating the ViewModel instance for this screen.
     * Subclasses must implement this to provide their specific [StelleViewModel].
     */
    @Composable
    abstract fun createViewModel(): V

    /**
     * A helper function for subclasses to send an [StelleIntent] to the ViewModel.
     * It safely queues intents if the ViewModel is not yet initialized.
     */
    protected fun sendIntent(intent: StelleIntent) {
        if (!this::viewModel.isInitialized) {
            delayedIntent.add(intent)
            return
        }
        viewModel.handleIntent(intent)
    }

    private fun sendDelayedIntents() {
        delayedIntent.forEach {
            viewModel.handleIntent(it)
        }
        delayedIntent.clear()
    }
}

/**
 * Remembers a [Flow] that is aware of the Composable's lifecycle.
 * The flow emits data only when the lifecycle is at least in the [minActiveState].
 * This is a crucial optimization to prevent re-subscribing to flows on every recomposition.
 *
 * @param flow The original flow to be lifecycle-aware.
 * @param lifecycle The lifecycle to observe, defaulting to the current `LocalLifecycleOwner`.
 * @param minActiveState The minimum lifecycle state required for the flow to be active.
 */
@Composable
inline fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}