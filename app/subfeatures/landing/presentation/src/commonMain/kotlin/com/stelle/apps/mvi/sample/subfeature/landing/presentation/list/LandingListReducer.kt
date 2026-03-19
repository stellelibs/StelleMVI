package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list

import com.stelle.apps.mvi.sample.subfeature.landing.presentation.LandingState
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event.LandingListEvent
import com.stelle.libs.mvi.StelleReducer

class LandingListReducer : StelleReducer<LandingState, LandingListEvent> {
    override fun reduce(previousState: LandingState, event: LandingListEvent) =
        previousState.copy(landingListState = reduce(previousState.landingListState, event))

    private fun reduce(previousState: LandingListState, event: LandingListEvent) = when (event) {
        is LandingListEvent.PokemonLoaded -> {
            previousState.copy(
                listPokemons = previousState.listPokemons + event.pokemons.results,
                loading = false,
                isErrorPokemon = false
            )
        }

        is LandingListEvent.PokemonDetailLoaded -> {
            previousState.copy(
                pokemonsDetail = previousState.pokemonsDetail + (event.pokemon.name to event.pokemon)
            )

        }

        is LandingListEvent.PokemonDetailError -> previousState.copy(
            pokemonsError = previousState.pokemonsError + event.name
        )

        is LandingListEvent.PokemonLoadedError -> previousState.copy(
            isErrorPokemon = true
        )

        is LandingListEvent.PokemonLoading -> previousState.copy(
            loading = true,
            isErrorPokemon = false
        )

        is LandingListEvent.FinishPagination -> previousState.copy(finishedPagination = true)
        is LandingListEvent.PokemonDetailClearError -> previousState.copy(
            pokemonsError = previousState.pokemonsError.filter { it != event.name }
        )
    }


}

