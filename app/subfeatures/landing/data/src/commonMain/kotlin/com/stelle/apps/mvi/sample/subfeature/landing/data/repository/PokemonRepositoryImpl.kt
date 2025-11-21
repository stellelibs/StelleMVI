package com.stelle.apps.mvi.sample.subfeature.landing.data.repository

import com.stelle.apps.mvi.sample.subfeature.landing.data.source.PokeRemoteSource
import com.stelle.apps.mvi.sample.subfeature.landing.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val source: PokeRemoteSource) : PokemonRepository {
    override suspend fun fetchPokemons(
        limit: Int,
        offset: Int
    ) = source.getPokemons(limit, offset)

    override suspend fun fetchPokemonDetail(nameOrId: String) = source.getPokemonDetail(nameOrId)
}