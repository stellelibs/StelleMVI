package com.stelle.apps.mvi.sample.subfeature.landing.data.source

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.api.PokeApiService
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.mapper.PokeRemoteMapper
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons
import com.stelle.libs.usecase.result.StelleResult


class PokeRemoteSourceImpl(private val api: PokeApiService, private val mapper: PokeRemoteMapper) :
    PokeRemoteSource {
    override suspend fun getPokemons(
        limit: Int,
        offset: Int
    ): StelleResult<Pokemons> {
        try {
            val response = api.getPokemons(limit, offset)
            return StelleResult.Success(mapper.toModel(response))
        } catch (e: Exception) {
            return StelleResult.Error(e)
        }
    }

    override suspend fun getPokemonDetail(nameOrId: String): StelleResult<PokemonDetail> {
        try {
            val response = api.getPokemonDetail(nameOrId)
            return StelleResult.Success(mapper.toModel(response))
        } catch (e: Exception) {
            return StelleResult.Error(e)
        }
    }

}