package com.stelle.libs.mvi.koin.di

import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import org.koin.ext.getFullName
import org.koin.mp.KoinPlatform.getKoin
import kotlin.reflect.KClass

/**
 * Creates or retrieves a Koin [Scope] whose ID and qualifier are derived from the given [kClass].
 *
 * This is the canonical way for StelleViewModel classes to obtain their dedicated Koin scope,
 * ensuring a consistent scope-ID convention across [StelleViewModelKoin] and [StelleChildViewModelKoin].
 *
 * @param kClass The class whose fully-qualified name becomes the scope ID.
 * @param source Optional source object to attach to the scope (typically the ViewModel instance).
 * @return The existing or newly created [Scope].
 */
internal fun <T : Any> createStelleScope(kClass: KClass<out T>, source: T): Scope =
    getKoin().getOrCreateScope(
        scopeId = kClass.getFullName(),
        qualifier = TypeQualifier(kClass),
        source = source
    )
