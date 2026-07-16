package com.unibo.mobile.uicompose.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun InputSection(
    modifier: Modifier = Modifier,
    alignBottom: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val arrangement = if (alignBottom) {
        Arrangement.spacedBy(UiConstants.BUTTON_SPACING, Alignment.Bottom)
    } else {
        Arrangement.spacedBy(UiConstants.BUTTON_SPACING)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = arrangement
    ) {
        content()
    }
}