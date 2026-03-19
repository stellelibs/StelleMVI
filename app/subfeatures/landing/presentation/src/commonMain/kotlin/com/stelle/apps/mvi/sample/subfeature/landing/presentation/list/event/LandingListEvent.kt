package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons
import com.stelle.libs.mvi.event.StelleEvent

sealed class LandingListEvent : StelleEvent {
    class PokemonLoaded(val pokemons: Pokemons) : LandingListEvent()
    data object PokemonLoading : LandingListEvent()
    data object PokemonLoadedError : LandingListEvent()
    class PokemonDetailLoaded(val pokemon: PokemonDetail) : LandingListEvent()
    class PokemonDetailError(val name: String) : LandingListEvent()
    class PokemonDetailClearError(val name: String) : LandingListEvent()
    data object FinishPagination : LandingListEvent()
}