package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.LandingListState
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.event.LandingListIntent
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui.composables.ErrorChargingList
import com.stelle.libs.mvi.event.StelleIntent


@Composable
fun PokemonList(
    state: LandingListState,
    onIntents: (intent: StelleIntent) -> Unit
) {
    val listState = rememberLazyListState()
    val isScrollToBottom = remember(listState) {
        derivedStateOf {
            val index = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val count = listState.layoutInfo.totalItemsCount - 1
            listState.isScrollInProgress.not() && index == count
        }
    }
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.listPokemons) { pokemon ->
            PokemonListItem(
                pokemon = pokemon,
                details = state.pokemonsDetail[pokemon.name],
                errorDetail = state.pokemonsError.contains(pokemon.name),
                rechargeOnError = { name ->
                    onIntents(
                        LandingListIntent.RechargeDetail(name)
                    )
                },
                onClick = { details ->
                    onIntents(LandingListIntent.OnClickDetail(details))
                })

        }
        item {
            if (state.listPokemons.isNotEmpty()) {
                if (state.isErrorPokemon) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorChargingList(onRetry = {
                            onIntents(LandingListIntent.RechargePokemons)
                        })
                    }
                }

                if (state.loading && !state.isErrorPokemon)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
            }
        }

    }
    LaunchedEffect(isScrollToBottom.value) {
        if (isScrollToBottom.value && !state.loading) {
            onIntents(LandingListIntent.LoadMode)
        }
    }
}