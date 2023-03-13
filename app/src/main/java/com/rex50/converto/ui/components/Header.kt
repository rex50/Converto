package com.rex50.converto.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rex50.converto.R
import com.rex50.converto.ui.theme.ConvertoTheme

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    onRefresh: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        RoundedButtonWithIcon(
            text = "Update",
            icon = Icons.Default.Refresh
        ) {
            onRefresh()
        }
    }

}

@Preview
@Composable
fun HeaderPreview() {
    ConvertoTheme {
        Surface {
            Header(title = stringResource(id = R.string.app_name)) {

            }
        }
    }
}