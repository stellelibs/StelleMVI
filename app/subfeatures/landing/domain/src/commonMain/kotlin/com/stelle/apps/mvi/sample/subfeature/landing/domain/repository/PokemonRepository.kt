package com.stelle.apps.mvi.sample.subfeature.landing.domain.repository

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons
import com.stelle.libs.usecase.result.StelleResult

interface PokemonRepository {
    suspend fun fetchPokemons(limit: Int = 20, offset: Int = 0): StelleResult<Pokemons>
    suspend fun fetchPokemonDetail(nameOrId: String): StelleResult<PokemonDetail>
}