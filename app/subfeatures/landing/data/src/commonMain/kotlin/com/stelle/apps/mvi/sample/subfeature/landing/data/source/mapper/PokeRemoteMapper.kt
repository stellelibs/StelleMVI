package com.stelle.apps.mvi.sample.subfeature.landing.data.source.mapper

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonDetailEntity
import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonResponseEntity
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons

interface PokeRemoteMapper {
    fun toModel(entity: PokemonResponseEntity): Pokemons
    fun toModel(entity: PokemonDetailEntity): PokemonDetail
}