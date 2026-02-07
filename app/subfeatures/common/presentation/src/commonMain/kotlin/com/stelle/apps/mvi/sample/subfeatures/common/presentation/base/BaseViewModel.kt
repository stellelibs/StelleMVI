package com.stelle.apps.mvi.sample.subfeatures.common.presentation.base


import com.stelle.libs.dispatchers.StelleDispatchers
import com.stelle.libs.mvi.StelleData
import com.stelle.libs.mvi.StelleReducer
import com.stelle.libs.mvi.StelleState
import com.stelle.libs.mvi.event.StelleEvent
import com.stelle.libs.mvi.koin.StelleViewModelKoin
import com.stelle.libs.usecase.StelleUseCase
import com.stelle.libs.usecase.executeUseCase
import kotlinx.coroutines.Job

/**
 * A base class for ViewModels within this application, providing common functionality.
 *
 * This class extends [StelleViewModelKoin] and integrates a [StelleDispatchers] instance
 * to simplify use case execution. It offers a convenient [executeUseCase] method that
 * automatically uses the provided dispatchers.
 *
 * @param State The type of the state managed by this ViewModel.
 * @param Event The type of the events this ViewModel can process.
 * @param data The [StelleData] instance that holds the state and effect flows.
 * @param reducer The [StelleReducer] responsible for state transformations.
 * @param dispatchers The [StelleDispatchers] instance to be used for executing use cases.
 */
abstract class BaseViewModel<State : StelleState, Event : StelleEvent>(
    data: StelleData<State>,
    private val dispatchers: StelleDispatchers,
    reducer: StelleReducer<State, Event> = object : StelleReducer<State, Event> {}
) : StelleViewModelKoin<State, Event>(data, reducer) {

    /**
     * Convenience constructor that creates a default [StelleData] implementation.
     *
     * Use this constructor when you don't need a custom [StelleData] implementation and just want
     * to provide the initial state.
     *
     * @param initialState The initial state of the ViewModel.
     * @param reducer The [StelleReducer] for this ViewModel's specific events. Defaults to a no-op reducer if not provided.
     * @param dispatchers The dispatchers provider.
     */
    constructor(
        initialState: State,
        dispatchers: StelleDispatchers,
        reducer: StelleReducer<State, Event> = object : StelleReducer<State, Event> {}
    ) : this(
        data = object : StelleData<State>(initialState) {},
        reducer = reducer,
        dispatchers = dispatchers
    )

    /**
     * Executes a [StelleUseCase] using the dispatchers provided to this ViewModel.
     *
     * This is a helper method that simplifies calls to the global `executeUseCase` extension
     * by automatically supplying the `dispatchers` instance held by this ViewModel.
     *
     * @param useCase The instance of the [StelleUseCase] to be executed.
     * @param params The parameters required for the use case execution.
     * @param onSuccess A callback invoked with the result upon successful execution.
     * @param onError A callback invoked with the `Throwable` in case of an error.
     * @return A [Job] representing the coroutine executing the use case.
     */
    fun <Params, R> executeUseCase(
        useCase: StelleUseCase<Params, R>,
        params: Params,
        onSuccess: (R) -> Unit,
        onError: (Throwable) -> Unit
    ): Job = executeUseCase(
        useCase,
        params,
        onSuccess,
        onError,
        dispatchers
    )

}