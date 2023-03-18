package com.rex50.converto.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rex50.converto.ui.theme.ConvertoTheme

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 8.dp,
                end = 8.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            text = message,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.secondary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundedButtonWithIcon(
            text = "Retry",
            icon = Icons.Default.Refresh
        ) {
            onRetry()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorCardPreview() {
    ConvertoTheme {
        Surface {
            ErrorCard(message = "Some Problem") {

            }
        }
    }
}