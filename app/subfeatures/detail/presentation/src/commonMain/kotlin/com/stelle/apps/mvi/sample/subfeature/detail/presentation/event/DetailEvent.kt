package com.stelle.apps.mvi.sample.subfeature.detail.presentation.event

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.libs.mvi.event.StelleEvent

sealed class DetailEvent : StelleEvent {
    data class OnPokemonDetail(val detail: PokemonDetail) : DetailEvent()
}