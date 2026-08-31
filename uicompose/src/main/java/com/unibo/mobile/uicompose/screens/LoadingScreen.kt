package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.uicompose.components.common.UiConstants

@Preview
@Composable
fun LoadingScreen() {
    println("DEBUG: Accessing LoadingScreen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = UiConstants.BASE_OVERLAY_ALPHA)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}