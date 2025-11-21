package com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase

import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons
import com.stelle.libs.usecase.StelleUseCase

interface GetPokemonUseCase : StelleUseCase<GetPokemonUseCase.Params, Pokemons> {
    data class Params(val limit: Int = 20, val offset: Int = 0)
}