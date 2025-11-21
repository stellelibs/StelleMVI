package com.stelle.libs.usecase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stelle.libs.dispatchers.IODispatcher
import com.stelle.libs.dispatchers.StelleDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Executes a [StelleUseCase] within the `viewModelScope` of this [ViewModel].
 *
 * This extension function simplifies the process of running business logic from a ViewModel.
 * It automatically handles the coroutine lifecycle and ensures that the `onSuccess` and `onError`
 * callbacks are invoked on the appropriate dispatchers.
 *
 * By default, the use case is executed on a background dispatcher (`io`) and the result is delivered
 * on the main UI dispatcher (`ui`).
 *
 * @param useCase The instance of the [StelleUseCase] to be executed.
 * @param params The parameters required for the use case execution. Use `Unit` if none are needed.
 * @param onSuccess A callback that will be invoked with the result `R` upon successful execution.
 * @param onError A callback that will be invoked with the `Throwable` if the execution fails.
 * @param dispatchers An instance of [StelleDispatchers] to customize the UI and background dispatchers.
 * @return A [Job] representing the coroutine that is executing the use case.
 *
 * @see StelleUseCase.execute
 */
fun <Params, R> ViewModel.executeUseCase(
    useCase: StelleUseCase<Params, R>,
    params: Params,
    onSuccess: (R) -> Unit,
    onError: (Throwable) -> Unit,
    dispatchers: StelleDispatchers = StelleDispatchers()
): Job = executeUseCase(
    useCase,
    params,
    onSuccess,
    onError,
    dispatchers.ui,
    dispatchers.io
)

/**
 * Executes a [StelleUseCase] within the `viewModelScope` of this [ViewModel].
 *
 * This is an overload of [executeUseCase] that allows specifying the background and UI dispatchers directly.
 *
 * @param dispatcherBg The [CoroutineDispatcher] on which the use case will be executed (background thread).
 * @param dispatcherUi The [CoroutineDispatcher] on which the `onSuccess` or `onError` callbacks will be invoked (main thread).
 *
 * @see executeUseCase
 */
fun <Params, R> ViewModel.executeUseCase(
    useCase: StelleUseCase<Params, R>,
    params: Params,
    onSuccess: (R) -> Unit,
    onError: (Throwable) -> Unit,
    dispatcherBg: CoroutineDispatcher = IODispatcher,
    dispatcherUi: CoroutineDispatcher = Dispatchers.Main
): Job =
    useCase.execute(
        this.viewModelScope,
        params,
        onSuccess,
        onError,
        dispatcherBg,
        dispatcherUi
    )