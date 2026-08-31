package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.uicompose.R
import com.unibo.mobile.uicompose.components.common.UiConstants

@Composable
fun EndScreen(
    modifier: Modifier = Modifier,
    isWon: Boolean,
    onNavigateToMenu: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(UiConstants.EDGE_BASE_PADDING),
        verticalArrangement = Arrangement.spacedBy(UiConstants.SECTION_SPACING),
    ) {
        val endText = if (isWon) {
            stringResource(R.string.end_victory)
        } else {
            stringResource(R.string.end_defeat)
        }
        Text(
            text = endText,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onNavigateToMenu,
            modifier = Modifier
                .fillMaxWidth()
                .height(UiConstants.BUTTON_HEIGHT)
        ) {
            Text(stringResource(R.string.return_to_menu))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndScreenVictoryPreview() {
    EndScreen(
        isWon = true,
        onNavigateToMenu = {}
    )
}
