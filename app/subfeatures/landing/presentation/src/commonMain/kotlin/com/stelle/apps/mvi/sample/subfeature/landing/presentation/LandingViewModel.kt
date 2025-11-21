package com.stelle.apps.mvi.sample.subfeature.landing.presentation

import com.stelle.apps.mvi.sample.subfeature.landing.presentation.event.LandingEvent
import com.stelle.apps.mvi.sample.subfeatures.common.presentation.base.BaseViewModel
import com.stelle.libs.dispatchers.StelleDispatchers

class LandingViewModel(data: LandingData, reducer: LandingReducer, dispatchers: StelleDispatchers) :
    BaseViewModel<LandingState, LandingEvent>(data, reducer, dispatchers)