package com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase

import com.stelle.apps.mvi.sample.subfeature.landing.domain.repository.PokemonRepository

class GetPokemonDetailUseCaseImpl(private val repository: PokemonRepository) :
    GetPokemonDetailUseCase {

    override suspend fun invoke(params: GetPokemonDetailUseCase.Params) =
        repository.fetchPokemonDetail(params.nameOrId)


}