package com.stelle.apps.mvi.sample.subfeature.detail.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.DetailState
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.DetailViewModel
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.composables.DetailHeader
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.event.DetailIntent
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.ui.composable.DetailBody
import com.stelle.libs.mvi.compose.koin.StelleScreenKoin
import com.stelle.libs.mvi.event.StelleIntent

class DetailScreen(
    detail: PokemonDetail,
    private val goBack: () -> Boolean
) :
    StelleScreenKoin<DetailState, DetailViewModel>() {

    init {
        sendIntent(DetailIntent.Init(detail))
    }

    @Composable
    override fun Content(
        state: DetailState,
        onIntents: (intent: StelleIntent) -> Unit
    ) {
        Scaffold(
            topBar = {
                DetailHeader(state.detail?.name, onBack = {
                    goBack()
                })
            }
        ) { paddingValues ->
            DetailBody(state.detail, paddingValues)
        }
    }


}