package com.rex50.converto.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import com.rex50.converto.BuildConfig

@Composable
fun AboutSheet(
    info: List<Pair<String, String>> = listOf(
        "Disclaimer" to "https://openexchangerates.org/terms",
        "License" to "https://openexchangerates.org/license",
        "Developed by Pavitra" to "",
        "Created with ❤ in Bengaluru" to "",
        "Version ${BuildConfig.VERSION_NAME}" to ""
    ),
    onDismissRequest: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    BottomSheetMenu(
        notSetLabel = "About",
        items = info,
        onItemSelected = { _, item ->
            // open browser if url is present
            if(item.second.isNotBlank())
                uriHandler.openUri(item.second)
        },
        onDismissRequest = onDismissRequest,
        drawItem = { item, selected, itemEnabled, onClick ->
            BottomSheetMenuItem(
                text = item.first,
                selected = selected,
                enabled = itemEnabled,
                onClick = onClick,
            )
        }
    )
}