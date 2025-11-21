package com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorView(
    message: String,
    buttonMessage: String? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {

        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = modifier
        )
        if (buttonMessage != null)
            Button(onClick = onClick) {
                Text(buttonMessage)
            }

    }
}
