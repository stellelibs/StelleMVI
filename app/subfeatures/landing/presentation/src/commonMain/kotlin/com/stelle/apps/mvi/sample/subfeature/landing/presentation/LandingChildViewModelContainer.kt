package com.stelle.apps.mvi.sample.subfeature.landing.presentation

import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.LandingListViewModel
import com.stelle.libs.mvi.StelleChildViewModel
import com.stelle.libs.mvi.StelleChildViewModelContainer

class LandingChildViewModelContainer(landingListViewModel: LandingListViewModel) :
    StelleChildViewModelContainer<LandingState> {
    override val childrenViewModels: List<StelleChildViewModel<LandingState, *>> =
        listOf(landingListViewModel)
}