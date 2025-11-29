package com.stelle.apps.mvi.sample.subfeature.landing.data.source.api

import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonDetailEntity
import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonResponseEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class PokeApiServiceImpl(private val client: HttpClient, private val baseUrl: String) :
    PokeApiService {
    override suspend fun getPokemons(
        limit: Int,
        offset: Int
    ): PokemonResponseEntity {
        return client.get("${baseUrl}$POKEMON_URL") {
            url {
                parameters.append(LIMIT_PARAM, limit.toString())
                parameters.append(OFFSET_PARAM, offset.toString())
            }
        }.body()
    }

    override suspend fun getPokemonDetail(nameOrId: String): PokemonDetailEntity {
        return client.get("${baseUrl}$POKEMON_URL/$nameOrId")
            .body()
    }
}

private const val LIMIT_PARAM = "limit"
private const val OFFSET_PARAM = "offset"
private const val POKEMON_URL = "/pokemon"