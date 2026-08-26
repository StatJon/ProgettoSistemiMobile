package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import com.unibo.mobile.uicompose.R

@Composable
fun EndScreen (
    modifier: Modifier = Modifier,
    isWon: Boolean,
    onNavigateToMenu: () -> Unit,
) {
    Column() {
        val endText = if (isWon) {
            stringResource(R.string.end_victory)
        }else{
            stringResource(R.string.end_defeat)
        }
        Text(endText)
        Button(onClick = onNavigateToMenu) {
            Text(stringResource(R.string.return_to_menu))
        }
    }
}