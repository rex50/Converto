package com.rex50.converto.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import com.rex50.converto.BuildConfig
import com.rex50.converto.R
import com.rex50.converto.ui.components.core.BottomSheetMenu
import com.rex50.converto.ui.components.core.BottomSheetMenuItem

@Composable
fun AboutSheet(
    info: List<Pair<String, String>> = listOf(
        stringResource(R.string.disclaimer) to stringResource(R.string.disclaimer_url),
        stringResource(R.string.license) to stringResource(R.string.license_url),
        stringResource(R.string.developed_by) to "",
        stringResource(R.string.created_in) to "",
        stringResource(id = R.string.version, BuildConfig.VERSION_NAME) to ""
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