package com.unibo.mobile.uicompose.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StandardButtonText(
    text: String,
    onTap: () -> Unit,
    enabled: Boolean = true
) {
    StandardButtonComposable(
        onTap = onTap,
        enabled = enabled
    ) {
        Text(text)
    }
}