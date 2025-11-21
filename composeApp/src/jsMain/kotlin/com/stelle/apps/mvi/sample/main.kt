package com.stelle.apps.mvi.sample

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavController
import androidx.navigation.bindToBrowserNavigation
import com.stelle.apps.mvi.sample.di.initKoin
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalBrowserHistoryApi
fun main() {
    initKoin()


    onWasmReady {
        val body = document.body ?: return@onWasmReady
        ComposeViewport(body) {
            App(
                onNavHostReady = ::onNavHostReady
            )
        }
    }
}

@ExperimentalBrowserHistoryApi
private suspend fun onNavHostReady(navController: NavController) {
    navController.bindToBrowserNavigation {
        "StelleMVISample"
    }

}