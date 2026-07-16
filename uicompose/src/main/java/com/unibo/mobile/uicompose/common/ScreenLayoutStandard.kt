package com.unibo.mobile.uicompose.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unibo.mobile.uicompose.common.UiConstants

@Composable
fun ScreenLayoutStandard(
    isLandscape: Boolean,
    displayWeight: Float = 0.5f,
    inputWeight: Float = 0.5f,
    inputAlignBottom: Boolean = true,
    displayContent: @Composable () -> Unit,
    inputContent: @Composable () -> Unit
) {
    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(UiConstants.BASE_PADDING)
        ) {
            DisplaySection(modifier = Modifier.weight(displayWeight)) {
                displayContent()
            }
            InputSection(modifier = Modifier.weight(inputWeight), alignBottom = inputAlignBottom) {
                inputContent()
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(UiConstants.BASE_PADDING)
        ) {
            DisplaySection(modifier = Modifier.weight(displayWeight)) {
                displayContent()
            }
            InputSection(modifier = Modifier.weight(inputWeight), alignBottom = inputAlignBottom) {
                inputContent()
            }
        }
    }
}