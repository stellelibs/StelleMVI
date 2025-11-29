package com.stelle.apps.mvi.sample.di

import com.stelle.libs.dispatchers.StelleDispatchers
import org.koin.dsl.module

internal fun presentationModule() = module {
    factory { StelleDispatchers() }
}
