package com.unibo.mobile.uicompose.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenLayoutStandard(
    isLandscape: Boolean,
    displayWeight: Float = 0.5f,
    inputWeight: Float = 0.5f,
    displayContent: @Composable () -> Unit,
    inputContent: @Composable () -> Unit
) {
    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            DisplaySection(modifier = Modifier.weight(displayWeight)) {
                displayContent()
            }
            InputSection(modifier = Modifier.weight(inputWeight)) {
                inputContent()
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            DisplaySection(modifier = Modifier.weight(displayWeight)) {
                displayContent()
            }
            InputSection(modifier = Modifier.weight(inputWeight)) {
                inputContent()
            }
        }
    }
}