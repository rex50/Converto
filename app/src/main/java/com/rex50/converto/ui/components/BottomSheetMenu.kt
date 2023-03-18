package com.rex50.converto.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog

@Composable
fun <T> BottomSheetMenu(
    notSetLabel: String? = null,
    items: List<T>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    onDismissRequest: () -> Unit,
    drawItem: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit,
) {
    BottomSheetDialog(
        onDismissRequest = onDismissRequest,
    ) {
        SheetContent(
            selectedIndex = selectedIndex,
            items = items,
            notSetLabel = notSetLabel,
            onItemSelected = { index, item ->
                onItemSelected(index, item)
            },
            drawItem = drawItem
        )
    }
}

@Composable
private fun <T> SheetContent(
    selectedIndex: Int = -1,
    notSetLabel: String? = null,
    items: List<T>,
    onItemSelected: (index: Int, item: T) -> Unit,
    drawItem: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit
) {
    MaterialTheme {
        Surface(
            shape = RoundedCornerShape(12.dp),
            //modifier = Modifier.fillMaxSize()
        ) {
            val listState = rememberLazyListState()

            LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                if (notSetLabel != null) {
                    item {
                        BottomSheetMenuItem(
                            text = notSetLabel,
                            selected = false,
                            enabled = false,
                            onClick = { },
                        )
                    }
                }
                itemsIndexed(items) { index, item ->
                    val selectedItem = index == selectedIndex
                    drawItem(
                        item,
                        selectedItem,
                        true
                    ) {
                        onItemSelected(index, item)
                    }

                    if (index < items.lastIndex) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetMenuItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        selected -> MaterialTheme.colorScheme.primary.copy(alpha = 1f)
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}