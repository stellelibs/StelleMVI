package com.stelle.apps.mvi.sample.subfeature.landing.data.source.mapper

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.Sprites
import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonDetailEntity
import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonEntity
import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonResponseEntity
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemon
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons

class PokeRemoteMapperImpl : PokeRemoteMapper {
    override fun toModel(entity: PokemonResponseEntity) = Pokemons(
        count = entity.count,
        results = entity.results.map { it.toModel() }
    )

    fun PokemonEntity.toModel() = Pokemon(name, url)

    override fun toModel(entity: PokemonDetailEntity) = PokemonDetail(
        id = entity.id,
        name = entity.name,
        height = entity.height,
        weight = entity.weight,
        sprites = Sprites(
            frontDefault = entity.sprites.frontDefault,
            frontShiny = entity.sprites.frontShiny
        )
    )


}