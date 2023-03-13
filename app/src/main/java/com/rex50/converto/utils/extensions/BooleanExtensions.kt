package com.rex50.converto.utils.extensions

import androidx.compose.ui.graphics.vector.ImageVector

fun Boolean.select(
    keyboardArrowUp: ImageVector,
    keyboardArrowDown: ImageVector
): ImageVector {
    return if (this) keyboardArrowUp else keyboardArrowDown
}