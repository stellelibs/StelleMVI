package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list

import com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase.GetPokemonDetailUseCase
import com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase.GetPokemonUseCase
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.LandingState
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event.LandingListEffect
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event.LandingListEvent
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event.LandingListIntent
import com.stelle.apps.mvi.sample.subfeatures.common.presentation.base.BaseChildViewModel
import com.stelle.libs.dispatchers.StelleDispatchers
import com.stelle.libs.mvi.event.StelleIntent

class LandingListViewModel(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    reducer: LandingListReducer,
    dispatchers: StelleDispatchers
) :
    BaseChildViewModel<LandingState, LandingListEvent>(reducer, dispatchers) {
    private val pageSize = 20
    private var offset = 0
    private var endListPokemon = false

    init {
        getPokemons()
    }

    private fun getPokemons() {
        if (endListPokemon) return
        sendEvent(LandingListEvent.onPokemonLoading)
        executeUseCase(
            getPokemonUseCase,
            GetPokemonUseCase.Params(limit = pageSize, offset = offset),
            onSuccess = { pokemons ->
                offset += pageSize
                endListPokemon = pokemons.count < pageSize
                sendEvent(LandingListEvent.onPokemonLoaded(pokemons))
                if (endListPokemon) sendEvent(LandingListEvent.onFinishPagination)
                pokemons.results.forEach { pokemon ->
                    getPokemonDetail(pokemon.name)
                }

            }, onError = {
                sendEvent(LandingListEvent.onPokemonLoadedError)
            }
        )
    }

    private fun getPokemonDetail(name: String) {
        sendEvent(LandingListEvent.onPokemonDetailClearError(name))
        executeUseCase(
            getPokemonDetailUseCase,
            GetPokemonDetailUseCase.Params(nameOrId = name),
            onSuccess = { pokemonDetail ->
                sendEvent(LandingListEvent.onPokemonDetailLoaded(pokemonDetail))
            }, onError = {
                sendEvent(LandingListEvent.onPokemonDetailError(name))

            }
        )
    }

    override fun handleIntent(intent: StelleIntent) = when (intent) {
        is LandingListIntent -> handleListIntent(intent)
        else -> super.handleIntent(intent)

    }

    private fun handleListIntent(intent: LandingListIntent) = when (intent) {
        LandingListIntent.LoadMode -> getPokemons()
        is LandingListIntent.RechargeDetail -> getPokemonDetail(intent.name)
        is LandingListIntent.OnClickDetail -> sendEffect(LandingListEffect.OpenDetails(intent.details))
        LandingListIntent.RechargePokemons -> getPokemons()
    }


}