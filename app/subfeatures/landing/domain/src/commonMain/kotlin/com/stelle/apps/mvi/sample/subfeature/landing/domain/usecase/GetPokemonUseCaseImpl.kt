package com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase

import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons
import com.stelle.apps.mvi.sample.subfeature.landing.domain.repository.PokemonRepository
import com.stelle.libs.usecase.result.StelleResult

class GetPokemonUseCaseImpl(private val repository: PokemonRepository) : GetPokemonUseCase {
    override suspend fun invoke(params: GetPokemonUseCase.Params): StelleResult<Pokemons> =
        repository.fetchPokemons(
            limit = params.limit,
            offset = params.offset
        )


}