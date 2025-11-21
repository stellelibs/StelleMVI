package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list

import androidx.compose.runtime.Immutable
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemon
import com.stelle.libs.mvi.StelleState

@Immutable
data class LandingListState(
    val loading: Boolean = true,
    val listPokemons: List<Pokemon> = emptyList(),
    val pokemonsDetail: Map<String, PokemonDetail> = emptyMap(),
    val pokemonsError: List<String> = emptyList(),
    val isErrorPokemon: Boolean = false,
    val finishedPagination: Boolean = false
) : StelleState