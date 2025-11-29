package com.stelle.apps.mvi.sample.subfeature.landing.presentation

import androidx.compose.runtime.Immutable
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.LandingListState
import com.stelle.libs.mvi.StelleState

@Immutable
data class LandingState(
    val landingListState: LandingListState = LandingListState()
) : StelleState