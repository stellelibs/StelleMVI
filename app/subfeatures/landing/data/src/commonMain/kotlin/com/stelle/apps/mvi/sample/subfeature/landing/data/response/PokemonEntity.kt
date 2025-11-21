package com.stelle.apps.mvi.sample.subfeature.landing.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PokemonEntity(val name: String, val url: String)