package com.stelle.apps.mvi.sample.subfeature.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetail(
    val name: String,
    val id: Int? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val sprites: Sprites? = null
)

@Serializable
data class Sprites(
    val frontDefault: String? = null,
    val frontShiny: String? = null
)
