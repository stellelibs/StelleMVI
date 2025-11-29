package com.stelle.apps.mvi.sample.subfeature.landing.di

fun getLandingSubfeatureModules() = listOf(
    dataModule(),
    domainModule(),
    presentationModule()
)