package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons
import com.stelle.libs.mvi.event.StelleEvent

sealed class LandingListEvent : StelleEvent {
    class onPokemonLoaded(val pokemons: Pokemons) : LandingListEvent()
    object onPokemonLoading : LandingListEvent()
    object onPokemonLoadedError : LandingListEvent()
    class onPokemonDetailLoaded(val pokemon: PokemonDetail) : LandingListEvent()

    class onPokemonDetailError(val name: String) : LandingListEvent()
    class onPokemonDetailClearError(val name: String) : LandingListEvent()
    object onFinishPagination : LandingListEvent()
}