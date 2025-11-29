package com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.libs.usecase.StelleUseCase

interface GetPokemonDetailUseCase : StelleUseCase<GetPokemonDetailUseCase.Params, PokemonDetail> {
    data class Params(val nameOrId: String)
}