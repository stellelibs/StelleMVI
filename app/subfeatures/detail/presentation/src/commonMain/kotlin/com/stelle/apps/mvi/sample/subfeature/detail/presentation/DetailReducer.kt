package com.stelle.apps.mvi.sample.subfeature.detail.presentation

import com.stelle.apps.mvi.sample.subfeature.detail.presentation.event.DetailEvent
import com.stelle.libs.mvi.StelleReducer
import com.stelle.libs.mvi.event.StelleEvent

class DetailReducer : StelleReducer<DetailState, StelleEvent> {
    override fun reduce(
        previousState: DetailState,
        event: StelleEvent
    ): DetailState {
        return when (event) {
            is DetailEvent.OnPokemonDetail -> {
                previousState.copy(
                    detail = event.detail
                )
            }

            else -> super.reduce(previousState, event)
        }
    }
}