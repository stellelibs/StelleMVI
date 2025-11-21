package com.stelle.apps.mvi.sample.subfeature.landing.di

import com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase.GetPokemonDetailUseCase
import com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase.GetPokemonDetailUseCaseImpl
import com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase.GetPokemonUseCase
import com.stelle.apps.mvi.sample.subfeature.landing.domain.usecase.GetPokemonUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal fun domainModule() = module {
    factoryOf(::GetPokemonDetailUseCaseImpl) bind GetPokemonDetailUseCase::class
    factoryOf(::GetPokemonUseCaseImpl) bind GetPokemonUseCase::class
}
