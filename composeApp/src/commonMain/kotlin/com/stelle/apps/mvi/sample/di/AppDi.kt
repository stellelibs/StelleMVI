package com.stelle.apps.mvi.sample.di

import com.stelle.apps.mvi.sample.subfeature.detail.di.getDetailSubfeatureModules
import com.stelle.apps.mvi.sample.subfeature.landing.di.getLandingSubfeatureModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        modules(
            listOf(
                dataModule(),
                presentationModule()
            ) + getDetailSubfeatureModules() + getLandingSubfeatureModules()
        )
        config?.invoke(this)
    }
}





