package com.stelle.apps.mvi.sample.subfeatures.common.presentation.base


import com.stelle.libs.dispatchers.StelleDispatchers
import com.stelle.libs.mvi.StelleChildViewModel
import com.stelle.libs.mvi.StelleReducer
import com.stelle.libs.mvi.StelleState
import com.stelle.libs.mvi.event.StelleEvent
import com.stelle.libs.usecase.StelleUseCase
import com.stelle.libs.usecase.executeUseCase
import kotlinx.coroutines.Job

/**
 * A base class for Child ViewModels within this application, providing common functionality.
 *
 * This class extends [StelleChildViewModel] and integrates a [StelleDispatchers] instance
 * to simplify use case execution. It offers a convenient [executeUseCase] method that
 * automatically uses the provided dispatchers.
 *
 * @param State The type of the shared state managed by the parent ViewModel.
 * @param Event The type of the events this ViewModel can process.
 * @param reducer The [StelleReducer] for this child's specific events. Defaults to a no-op reducer if not provided.
 * @param dispatchers The [StelleDispatchers] instance to be used for executing use cases.
 */
abstract class BaseChildViewModel<State : StelleState, Event : StelleEvent>(
    reducer: StelleReducer<State, Event> = object : StelleReducer<State, Event> {},
    protected val dispatchers: StelleDispatchers
) : StelleChildViewModel<State, Event>(reducer) {

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