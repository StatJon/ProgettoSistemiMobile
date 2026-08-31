package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.unibo.mobile.uicompose.components.common.UiConstants

@Composable
fun ActionMessagePopup(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = UiConstants.EDGE_BASE_PADDING),
        contentAlignment = Alignment.TopCenter,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(UiConstants.EDGE_BASE_PADDING),
                textAlign = TextAlign.Center
            )
        }
    }
}