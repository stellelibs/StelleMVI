package com.stelle.apps.mvi.sample.subfeature.detail.presentation.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import org.jetbrains.compose.resources.stringResource
import stellemvi.app.subfeatures.detail.presentation.generated.resources.Res
import stellemvi.app.subfeatures.detail.presentation.generated.resources.height
import stellemvi.app.subfeatures.detail.presentation.generated.resources.images
import stellemvi.app.subfeatures.detail.presentation.generated.resources.weight

@Composable
fun DetailBody(
    detail: PokemonDetail?, innerPadding: PaddingValues,
) {
    if (detail != null)
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        model = detail.sprites?.frontDefault ?: detail.sprites?.frontShiny,
                        contentDescription = detail.name,
                        modifier = Modifier.fillMaxSize()

                    )
                }

            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(6.dp))
                InfoRow(
                    label = stringResource(Res.string.height),
                    value = detail.height?.toString() ?: "-"
                )
                Spacer(modifier = Modifier.height(6.dp))
                InfoRow(
                    label = stringResource(Res.string.weight),
                    value = detail.weight?.toString() ?: "-"
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    stringResource(Res.string.images).capitalize(Locale.current),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    SmallSprite(url = detail.sprites?.frontDefault)
                    Spacer(modifier = Modifier.width(8.dp))
                    SmallSprite(url = detail.sprites?.frontShiny)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
}




