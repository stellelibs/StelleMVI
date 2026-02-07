package com.stelle.apps.mvi.sample.subfeature.landing.di

import com.stelle.apps.mvi.sample.subfeature.landing.presentation.LandingChildViewModelContainer
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.LandingState
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.LandingViewModel
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.LandingListReducer
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.LandingListViewModel
import com.stelle.libs.mvi.StelleChildViewModelContainer
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal fun presentationModule() = module {
    /* No Koin scope or binding is required for `LandingViewModel` because `LandingScreen`
     overrides the `viewModelClass` property, allowing Koin to resolve it directly.*/
    factory { LandingState() }
    viewModelOf(::LandingViewModel)


    scope<LandingViewModel> {
        factoryOf(::LandingListReducer)
        factoryOf(::LandingListViewModel)
        factory<StelleChildViewModelContainer<LandingState>> {
            LandingChildViewModelContainer(get())
        }
    }
}

