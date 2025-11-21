package com.stelle.apps.mvi.sample

import androidx.compose.ui.window.ComposeUIViewController
import com.stelle.apps.mvi.sample.di.initKoin

fun MainViewController() =
    ComposeUIViewController(configure = { initKoin() }) { App() }
