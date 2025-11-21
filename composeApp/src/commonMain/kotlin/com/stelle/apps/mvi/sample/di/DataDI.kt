package com.stelle.apps.mvi.sample.di

import com.stelle.apps.mvi.sample.subfeature.common.lib.di.DataDI.SERVER_BASE
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun dataModule() = module {
    single {
        HttpClient {
            contentNegotiation()
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(tag = "HTTP Client", message = message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
    factory(named(SERVER_BASE)) { SERVER }

}

private fun HttpClientConfig<*>.contentNegotiation() {
    install(ContentNegotiation) {
        json(json = Json {
            ignoreUnknownKeys = true
        }
        )
    }
}

private const val SERVER = "https://pokeapi.co/api/v2"
