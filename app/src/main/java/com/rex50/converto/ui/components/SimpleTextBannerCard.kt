package com.rex50.converto.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTextBannerCard(
    modifier: Modifier = Modifier,
    text: String,
    isVisible: Boolean = true
) {
    Box(
        modifier = modifier
            .animateContentSize()
    ) {
        DefaultAnimatedVisibility(isVisible = isVisible) {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp
                    )
            )
        }
    }
}