package com.stelle.libs.mvi.koin

import com.stelle.libs.mvi.StelleChildViewModel
import com.stelle.libs.mvi.StelleChildViewModelContainer
import com.stelle.libs.mvi.StelleReducer
import com.stelle.libs.mvi.StelleState
import com.stelle.libs.mvi.event.StelleEvent
import com.stelle.libs.mvi.koin.di.injectChildrenContainer
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import org.koin.ext.getFullName
import org.koin.mp.KoinPlatform.getKoin

/**
 * An abstract [StelleChildViewModel] that integrates with Koin, allowing it to have its own
 * child ViewModels.
 *
 * This class enables the creation of nested ViewModel hierarchies. It creates its own Koin [Scope]
 * to manage the lifecycle of its children, which are injected via the [children] property.
 *
 * @param State The type of the shared state, which must extend [StelleState].
 * @param Event The type of events this child ViewModel can process.
 * @param reducer The [StelleReducer] for this child's specific events. Defaults to a no-op reducer if not provided.
 */
abstract class StelleChildViewModelKoin<State : StelleState, Event : StelleEvent>(
    reducer: StelleReducer<State, Event> = object : StelleReducer<State, Event> {}
) : StelleChildViewModel<State, Event>(reducer) {

    private fun getScope() = getKoin().getOrCreateScope(
        scopeId = this::class.getFullName(),
        qualifier = TypeQualifier(this::class),
        source = this
    )

    /**
     * Overridden to automatically inject a [StelleChildViewModelContainer] for this ViewModel's
     * own children, using its dedicated Koin scope.
     */
    override val children: StelleChildViewModelContainer<State>? =
        getScope().injectChildrenContainer()
}