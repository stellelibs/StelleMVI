package com.stelle.apps.mvi.sample.subfeature.landing.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailEntity(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: SpritesEntity
)

@Serializable
data class SpritesEntity(
    @SerialName("front_default") val frontDefault: String? = null,
    @SerialName("front_shiny") val frontShiny: String? = null
)
