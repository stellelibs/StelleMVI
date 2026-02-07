package com.stelle.apps.mvi.sample.subfeature.detail.presentation

import com.stelle.apps.mvi.sample.subfeature.detail.presentation.event.DetailEvent
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.event.DetailIntent
import com.stelle.apps.mvi.sample.subfeatures.common.presentation.base.BaseViewModel
import com.stelle.libs.dispatchers.StelleDispatchers
import com.stelle.libs.mvi.event.StelleEvent
import com.stelle.libs.mvi.event.StelleIntent

class DetailViewModel(data: DetailData, reducer: DetailReducer, dispatchers: StelleDispatchers) :
    BaseViewModel<DetailState, StelleEvent>(
        data = data,
        reducer = reducer,
        dispatchers = dispatchers
    ) {

    override fun handleIntent(intent: StelleIntent) = when (intent) {
        is DetailIntent -> handleIntentDetail(intent)
        else -> super.handleIntent(intent)
    }

    private fun handleIntentDetail(intent: DetailIntent) = when (intent) {
        is DetailIntent.Init -> {
            sendEvent(DetailEvent.OnPokemonDetail(intent.detail))
        }
    }
}