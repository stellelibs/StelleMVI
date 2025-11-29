package com.stelle.apps.mvi.sample.subfeature.detail.di

import com.stelle.apps.mvi.sample.subfeature.detail.presentation.DetailData
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.DetailReducer
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.DetailViewModel
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.ui.DetailScreen
import com.stelle.libs.mvi.StelleViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal fun presentationModule() = module {

    scope<DetailScreen> {
        factory { DetailData() }
        factoryOf(::DetailReducer)
        viewModelOf(::DetailViewModel) bind StelleViewModel::class
    }
}
