package com.stelle.apps.mvi.sample.subfeature.landing.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponseEntity(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonEntity>
)