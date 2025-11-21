package com.stelle.libs.mvi

/**
 * Defines a container for grouping multiple [StelleChildViewModel] instances that are managed
 * by a single [StelleParentViewModel].
 *
 * This allows a parent to orchestrate a collection of children that share a common state.
 *
 * @param State The type of the shared [StelleState] managed by the child ViewModels.
 */
interface StelleChildViewModelContainer<State : StelleState> {
    /**
     * The list of [StelleChildViewModel] instances managed by this container.
     */
    val childrenViewModels: List<StelleChildViewModel<State, *>>
}