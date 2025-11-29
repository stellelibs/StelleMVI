package com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import stellemvi.app.subfeatures.landing.presentation.generated.resources.Res
import stellemvi.app.subfeatures.landing.presentation.generated.resources.error_charging_detail
import stellemvi.app.subfeatures.landing.presentation.generated.resources.retry

@Composable
fun ErrorChargingList(modifier: Modifier = Modifier, onRetry: () -> Unit) {
    ErrorView(
        message = stringResource(Res.string.error_charging_detail),
        buttonMessage = stringResource(Res.string.retry),
        onClick = onRetry,
        modifier = modifier
    )
}