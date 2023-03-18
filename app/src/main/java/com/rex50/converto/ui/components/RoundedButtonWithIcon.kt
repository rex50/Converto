package com.rex50.converto.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rex50.converto.ui.theme.ConvertoTheme

@Composable
fun RoundedButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.elevatedCardColors(),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(weight = 1f, fill = false)
            )
            Image(
                painter = rememberVectorPainter(image = icon),
                contentDescription = text,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(18.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoundedButtonWithIconPreview() {
    ConvertoTheme {
        Surface {
            RoundedButtonWithIcon(
                text = "Refresh",
                icon = Icons.Default.Refresh
            ) {
                
            }
        }
    }
}