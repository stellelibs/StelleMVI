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

abstract class BaseViewModel<State : StelleState, Event : StelleEvent>(
    data: StelleData<State>,
    reducer: StelleReducer<State, Event>,
    private val dispatchers: StelleDispatchers
) : StelleViewModelKoin<State, Event>(data, reducer) {
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