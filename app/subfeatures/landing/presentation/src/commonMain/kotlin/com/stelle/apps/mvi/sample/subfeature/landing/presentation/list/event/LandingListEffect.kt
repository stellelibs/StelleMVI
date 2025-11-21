package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.libs.mvi.event.StelleEffect

sealed class LandingListEffect : StelleEffect {
    data class OpenDetails(val details: PokemonDetail) : LandingListEffect()

}