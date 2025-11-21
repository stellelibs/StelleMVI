package com.stelle.libs.mvi.koin.di

import com.stelle.libs.mvi.StelleChildViewModelContainer
import com.stelle.libs.mvi.StelleState
import org.koin.core.scope.Scope

/**
 * Inject children container
 *
 * @param State
 * @param Event
 * @return
 */
internal inline fun <State : StelleState> Scope.injectChildrenContainer() =
    this.getOrNull<StelleChildViewModelContainer<State>>()