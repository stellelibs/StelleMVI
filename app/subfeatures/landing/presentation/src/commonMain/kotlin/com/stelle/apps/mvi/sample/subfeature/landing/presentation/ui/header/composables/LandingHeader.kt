package com.stelle.apps.mvi.sample.subfeature.landing.presentation.ui.header.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import stellemvi.app.subfeatures.landing.presentation.generated.resources.Res
import stellemvi.app.subfeatures.landing.presentation.generated.resources.pokemon_loaded_count
import stellemvi.app.subfeatures.landing.presentation.generated.resources.pokedex_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingHeader(pokemonCount: Int) {
    TopAppBar(
        title = { Text(stringResource(Res.string.pokedex_title)) },
        actions = {
            Text(
                text = stringResource(Res.string.pokemon_loaded_count, pokemonCount),
                modifier = Modifier.padding(end = 16.dp),
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}