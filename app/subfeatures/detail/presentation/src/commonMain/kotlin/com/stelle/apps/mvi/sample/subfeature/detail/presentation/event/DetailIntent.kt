package com.stelle.apps.mvi.sample.subfeature.detail.presentation.event

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.libs.mvi.event.StelleIntent

sealed class DetailIntent : StelleIntent {
    data class Init(val detail: PokemonDetail) : DetailIntent()
}