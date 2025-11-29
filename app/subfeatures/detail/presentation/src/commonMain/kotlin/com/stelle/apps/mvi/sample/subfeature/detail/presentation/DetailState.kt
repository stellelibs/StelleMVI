package com.stelle.apps.mvi.sample.subfeature.detail.presentation

import androidx.compose.runtime.Immutable
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.libs.mvi.StelleState

@Immutable
data class DetailState(val detail: PokemonDetail? = null) : StelleState