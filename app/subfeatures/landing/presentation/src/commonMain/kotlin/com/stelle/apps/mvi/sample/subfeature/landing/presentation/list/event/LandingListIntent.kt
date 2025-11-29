package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.libs.mvi.event.StelleIntent

sealed class LandingListIntent : StelleIntent {
    data object LoadMode : LandingListIntent()
    object RechargePokemons : LandingListIntent()

    data class RechargeDetail(val name: String) : LandingListIntent()
    data class OnClickDetail(val details: PokemonDetail) : LandingListIntent()

}