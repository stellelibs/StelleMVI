package com.stelle.apps.mvi.sample.subfeatures.common.presentation.base


import com.stelle.libs.dispatchers.StelleDispatchers
import com.stelle.libs.mvi.StelleChildViewModel
import com.stelle.libs.mvi.StelleReducer
import com.stelle.libs.mvi.StelleState
import com.stelle.libs.mvi.event.StelleEvent
import com.stelle.libs.usecase.StelleUseCase
import com.stelle.libs.usecase.executeUseCase
import kotlinx.coroutines.Job

abstract class BaseChildViewModel<State : StelleState, Event : StelleEvent>(
    reducer: StelleReducer<State, Event>,
    protected val dispatchers: StelleDispatchers
) : StelleChildViewModel<State, Event>(reducer) {
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