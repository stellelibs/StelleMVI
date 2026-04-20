package com.stelle.apps.mvi.sample.subfeature.landing.data.source

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.api.PokeApiService
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.mapper.PokeRemoteMapper
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons


class PokeRemoteSourceImpl(private val api: PokeApiService, private val mapper: PokeRemoteMapper) :
    PokeRemoteSource {
    override suspend fun getPokemons(
        limit: Int,
        offset: Int
    ): Result<Pokemons> {
        try {
            val response = api.getPokemons(limit, offset)
            return Result.success(mapper.toModel(response))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getPokemonDetail(nameOrId: String): Result<PokemonDetail> {
        try {
            val response = api.getPokemonDetail(nameOrId)
            return Result.success(mapper.toModel(response))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}