package com.stelle.libs.usecase

import com.stelle.libs.dispatchers.IODispatcher
import com.stelle.libs.dispatchers.StelleDispatchers
import com.stelle.libs.usecase.result.fold
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Launches a coroutine in the given [scope] to execute the use case and deliver the result.
 *
 * This function orchestrates the execution by:
 * 1. Launching a new coroutine on the specified UI dispatcher (`dispatcherUi`).
 * 2. Switching to a background dispatcher (`dispatcherBg`) using `withContext` to run the use case.
 * 3. Calling the appropriate callback (`onSuccess` or `onError`) on the UI dispatcher with the result.
 *
 * @param scope The [CoroutineScope] in which the coroutine will be launched (e.g., `viewModelScope`).
 * @param params The parameters required for the use case execution.
 * @param onSuccess The callback to be invoked with the successful result on the UI thread.
 * @param onError The callback to be invoked with the exception on the UI thread if an error occurs.
 * @param dispatchers An instance of [StelleDispatchers] to customize the UI and background dispatchers.
 * @return A [Job] representing the launched coroutine.
 */
fun <Params, R> StelleUseCase<Params, R>.execute(
    scope: CoroutineScope,
    params: Params,
    onSuccess: (R) -> Unit,
    onError: (Throwable) -> Unit,
    dispatchers: StelleDispatchers = StelleDispatchers()
) = execute(
    scope,
    params,
    onSuccess,
    onError,
    dispatchers.ui,
    dispatchers.io,

    )

/**
 * Launches a coroutine in the given [scope] to execute the use case and deliver the result.
 *
 * This is an overload of [execute] that allows specifying the background and UI dispatchers directly.
 *
 * @param dispatcherBg The [CoroutineDispatcher] on which the use case will be executed (e.g., `Dispatchers.IO`).
 * @param dispatcherUi The [CoroutineDispatcher] on which the result callbacks will be invoked (e.g., `Dispatchers.Main`).
 */
fun <Params, R> StelleUseCase<Params, R>.execute(
    scope: CoroutineScope,
    params: Params,
    onSuccess: (R) -> Unit,
    onError: (Throwable) -> Unit,
    dispatcherBg: CoroutineDispatcher = IODispatcher,
    dispatcherUi: CoroutineDispatcher = Dispatchers.Main
): Job =
    scope.launch(dispatcherUi) {
        val result = withContext(dispatcherBg) { invoke(params) }
        result.fold(onSuccess, onError)
    }
