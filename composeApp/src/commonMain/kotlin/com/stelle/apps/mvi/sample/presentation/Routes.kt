package com.stelle.apps.mvi.sample.presentation

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Landing : Routes()

    @Serializable
    data class Detail(val detail: PokemonDetail) : Routes()
}