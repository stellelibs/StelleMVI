package com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.LandingState
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.LandingViewModel
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event.LandingListEffect
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event.LandingListIntent
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.ui.composables.PokemonList
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui.composables.ErrorChargingList
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui.header.composables.LandingHeader
import com.stelle.libs.mvi.compose.koin.StelleScreenKoin
import com.stelle.libs.mvi.event.StelleEffect
import com.stelle.libs.mvi.event.StelleIntent

class LandingScreen(private val navigateToDetail: (detail: PokemonDetail) -> Unit) :
    StelleScreenKoin<LandingState, LandingViewModel>() {

    /**
     * By overriding `viewModelClass`, we explicitly tell Koin which ViewModel to inject for this screen.
     * This is an optional configuration that avoids the need for a Koin scope and binding `StelleViewModel::class`
     * in the dependency injection module.
     *
     * This allows simplifying the DI module declaration from:
     * ```kotlin
     * scope<LandingScreen> {
     *     factory { LandingData() }
     *     factoryOf(::LandingReducer)
     *     viewModelOf(::LandingViewModel) bind StelleViewModel::class
     * }
     * ```
     * to:
     * ```kotlin
     * factory { LandingData() }
     * factoryOf(::LandingReducer)
     * viewModelOf(::LandingViewModel)
     * ```
     */
    override val viewModelClass = LandingViewModel::class


    @Composable
    override fun Content(
        state: LandingState,
        onIntents: (intent: StelleIntent) -> Unit
    ) {
        Scaffold(
            topBar = {
                LandingHeader(pokemonCount = state.landingListState.listPokemons.size)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                PokemonList(
                    state = state.landingListState,
                    onIntents = onIntents
                )
                if (state.landingListState.loading && state.landingListState.listPokemons.isEmpty() && !state.landingListState.isErrorPokemon) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (state.landingListState.isErrorPokemon && state.landingListState.listPokemons.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorChargingList(modifier = Modifier.align(Alignment.Center), onRetry = {
                            onIntents(LandingListIntent.RechargePokemons)
                        })
                    }
                }
            }
        }
    }

    override fun onEffect(effect: StelleEffect) = when (effect) {
        is LandingListEffect -> onListEffect(effect)
        else -> super.onEffect(effect)
    }

    private fun onListEffect(effect: LandingListEffect) = when (effect) {
        is LandingListEffect.OpenDetails -> navigateToDetail(effect.details)
    }
}