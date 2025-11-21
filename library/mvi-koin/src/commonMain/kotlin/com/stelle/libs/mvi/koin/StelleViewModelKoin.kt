package com.stelle.libs.mvi.koin

import com.stelle.libs.mvi.StelleChildViewModelContainer
import com.stelle.libs.mvi.StelleData
import com.stelle.libs.mvi.StelleReducer
import com.stelle.libs.mvi.StelleState
import com.stelle.libs.mvi.StelleViewModel
import com.stelle.libs.mvi.event.StelleEvent
import com.stelle.libs.mvi.koin.di.injectChildrenContainer
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import org.koin.ext.getFullName
import org.koin.mp.KoinPlatform.getKoin

/**
 * An abstract [StelleViewModel] that integrates with Koin for automatic dependency injection
 * of its children.
 *
 * This class creates a dedicated Koin [Scope] tied to its own lifecycle, which is then used
 * to resolve its dependencies, particularly the [StelleChildViewModelContainer].
 *
 * @param State The type of the state managed by this ViewModel.
 * @param Event The type of the events this ViewModel can process.
 * @constructor
 *
 *  @param data The [StelleData] instance that holds the state and effect flows.
 * @param reducer The [StelleReducer] for this ViewModel's specific events.
 */
abstract class StelleViewModelKoin<State : StelleState, Event : StelleEvent>(
    data: StelleData<State>,
    reducer: StelleReducer<State, Event>
) : StelleViewModel<State, Event>(data, reducer) {

    private fun getScope() = getKoin().getOrCreateScope(
        scopeId = this::class.getFullName(),
        qualifier = TypeQualifier(this::class),
        source = this
    )

    /**
     * Overridden to automatically inject the [StelleChildViewModelContainer] from this
     * ViewModel's dedicated Koin scope.
     *
     * For this to work, you must define a `factory` for `StelleChildViewModelContainer<State>`
     * within a Koin scope associated with this ViewModel class. For example:
     * ```kotlin
     * scope<MyViewModel> {
     *     factory<StelleChildViewModelContainer<MyState>> { MyChildContainer(get()) }
     * }
     * ```
     */
    override val children: StelleChildViewModelContainer<State>? =
        getScope().injectChildrenContainer()

    init {
        // Automatically establishes the parent-child relationship upon creation.
        setParents()
    }
}