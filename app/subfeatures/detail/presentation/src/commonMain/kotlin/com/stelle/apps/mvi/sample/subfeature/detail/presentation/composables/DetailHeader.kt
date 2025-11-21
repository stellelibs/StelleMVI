package com.stelle.apps.mvi.sample.subfeature.detail.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import stellemvi.app.subfeatures.detail.presentation.generated.resources.Res
import stellemvi.app.subfeatures.detail.presentation.generated.resources.back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHeader(title: String?, onBack: () -> Unit) {
    TopAppBar(
        title = { if (title != null) Text(title.uppercase()) },
        navigationIcon = {
            IconButton(onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.back),
                    tint = MaterialTheme.colorScheme.onPrimary

                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}