package com.stelle.apps.mvi.sample.subfeature.detail.presentation.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import stellemvi.app.subfeatures.detail.presentation.generated.resources.Res
import stellemvi.app.subfeatures.detail.presentation.generated.resources.image

@Composable
internal fun SmallSprite(url: String?) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .size(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                url,
                contentDescription = stringResource(Res.string.image),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
