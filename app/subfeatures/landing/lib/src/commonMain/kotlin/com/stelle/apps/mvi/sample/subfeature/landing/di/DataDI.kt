package com.stelle.apps.mvi.sample.subfeature.landing.di

import com.stelle.apps.mvi.sample.subfeature.common.lib.di.DataDI.SERVER_BASE
import com.stelle.apps.mvi.sample.subfeature.landing.data.repository.PokemonRepositoryImpl
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.PokeRemoteSource
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.PokeRemoteSourceImpl
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.api.PokeApiService
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.api.PokeApiServiceImpl
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.mapper.PokeRemoteMapper
import com.stelle.apps.mvi.sample.subfeature.landing.data.source.mapper.PokeRemoteMapperImpl
import com.stelle.apps.mvi.sample.subfeature.landing.domain.repository.PokemonRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal fun dataModule() = module {
    factoryOf(::PokemonRepositoryImpl) bind PokemonRepository::class
    factoryOf(::PokeRemoteSourceImpl) bind PokeRemoteSource::class
    factoryOf(::PokeRemoteMapperImpl) bind PokeRemoteMapper::class
    factory<PokeApiService> { PokeApiServiceImpl(get(), get(named(SERVER_BASE))) }
}