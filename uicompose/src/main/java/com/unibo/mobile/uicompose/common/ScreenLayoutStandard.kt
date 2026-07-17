package com.unibo.mobile.uicompose.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenLayoutStandard(
    isLandscape: Boolean,
    modifier: Modifier = Modifier,
    displayWeight: Float = 0.5f,
    inputWeight: Float = 0.5f,
    displayContent: @Composable BoxScope.() -> Unit,
    inputContent: @Composable BoxScope.() -> Unit
) {
    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(UiConstants.EDGE_BASE_PADDING),
            horizontalArrangement = Arrangement.spacedBy(UiConstants.SECTION_SPACING)
        ) {
            Box(modifier = Modifier.weight(displayWeight)) { displayContent() }
            Box(modifier = Modifier.weight(inputWeight)) { inputContent() }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(UiConstants.EDGE_BASE_PADDING),
            verticalArrangement = Arrangement.spacedBy(UiConstants.SECTION_SPACING)
        ) {
            Box(modifier = Modifier.weight(displayWeight)) { displayContent() }
            Box(modifier = Modifier.weight(inputWeight)) { inputContent() }
        }
    }
}