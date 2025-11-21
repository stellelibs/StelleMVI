package com.stelle.apps.mvi.sample

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.stelle.apps.mvi.sample.presentation.Routes
import com.stelle.apps.mvi.sample.presentation.theme.AppTheme
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.detail.presentation.ui.DetailScreen
import com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui.LandingScreen
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.typeOf


@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    AppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.Landing,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            }

        ) {

            composable<Routes.Landing> {
                LandingScreen({ detail -> navController.navigate(Routes.Detail(detail)) }).Show()
            }
            composable<Routes.Detail>(typeMap = mapOf(typeOf<PokemonDetail>() to navTypeOf<PokemonDetail>())) {
                val detail = it.toRoute<Routes.Detail>().detail
                DetailScreen(
                    detail = detail,
                    goBack = { navController.popBackStack() }
                ).Show()
            }
        }
        LaunchedEffect(navController) {
            onNavHostReady(navController)
        }


    }
}


inline fun <reified T> navTypeOf(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun put(bundle: SavedState, key: String, value: T) {
        bundle.write { putString(key, json.encodeToString(value)) }
    }

    override fun serializeAsValue(value: T): String {
        return json.encodeToString(value).encode()
    }

    override fun get(bundle: SavedState, key: String): T? {
        return bundle.read { getString(key).let(json::decodeFromString) }
    }

    override fun parseValue(value: String): T = json.decodeFromString(value.decode())
}

fun String.encode(): String =
    buildString {
        for (c in this@encode) {
            if (c.isLetterOrDigit() || c in "-_.~") {
                append(c)
            } else {
                append('%')
                append(c.code.toString(16).uppercase().padStart(2, '0'))
            }
        }
    }


fun String.decode(): String {
    val result = StringBuilder()
    var i = 0
    while (i < length) {
        when (val c = this[i]) {
            '+' -> {
                result.append(' ')
                i++
            }

            '%' -> {
                if (i + 2 < length) {
                    val hex = substring(i + 1, i + 3)
                    val intValue = hex.toIntOrNull(16) ?: 0
                    result.append(intValue.toChar())
                    i += 3
                } else {
                    result.append(c)
                    i++
                }
            }

            else -> {
                result.append(c)
                i++
            }
        }
    }
    return result.toString()
}
