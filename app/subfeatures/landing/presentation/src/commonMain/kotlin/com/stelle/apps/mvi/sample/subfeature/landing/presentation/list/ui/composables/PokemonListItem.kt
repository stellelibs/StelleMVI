package com.stelle.apps.mvi.sample.subfeature.landing.presentation.list.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemon
import org.jetbrains.compose.resources.stringResource
import stellemvi.app.subfeatures.landing.presentation.generated.resources.Res
import stellemvi.app.subfeatures.landing.presentation.generated.resources.error_charging_detail
import stellemvi.app.subfeatures.landing.presentation.generated.resources.retry


@Composable
internal fun PokemonListItem(
    pokemon: Pokemon,
    details: PokemonDetail?,
    errorDetail: Boolean,
    rechargeOnError: (String) -> Unit,
    onClick: (PokemonDetail) -> Unit
) {
    Card(
        onClick = {
            if (details != null) {
                onClick(details)
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (details != null && details.sprites?.frontDefault != null) {
                    SubcomposeAsyncImage(
                        model = details.sprites?.frontDefault,
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(64.dp),
                        loading = {
                            CircularProgressIndicator()
                        }
                    )
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)

                    ) {
                        if (details == null && !errorDetail) {
                            CircularProgressIndicator()
                        }
                    }
                }


                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            if (errorDetail)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.error_charging_detail),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Button(onClick = { rechargeOnError(pokemon.name) }) {
                        Text(text = stringResource(Res.string.retry))
                    }
                }
        }
    }

}
