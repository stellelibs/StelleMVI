package com.stelle.apps.mvi.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.stelle.apps.mvi.sample.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "StelleMvi",
        ) {
            App()
        }
    }
}