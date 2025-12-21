package com.stelle.apps.mvi.sample.subfeature.landing.presentation

import com.stelle.apps.mvi.sample.subfeature.landing.presentation.event.LandingEvent
import com.stelle.apps.mvi.sample.subfeatures.common.presentation.base.BaseViewModel
import com.stelle.libs.dispatchers.StelleDispatchers

class LandingViewModel(landingState: LandingState, dispatchers: StelleDispatchers) :
    BaseViewModel<LandingState, LandingEvent>(landingState, dispatchers)