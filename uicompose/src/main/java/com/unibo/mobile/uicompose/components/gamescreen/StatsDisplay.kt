package com.unibo.mobile.uicompose.components.gamescreen

import com.unibo.mobile.uicompose.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Displays a list of stats in a Surface with vertical Column layout.
 *
 * @param infoList List of stat strings to display
 * @param modifier Modifier to apply to the Surface container
 */
@Composable
fun StatsDisplay(
    infoList: List<String>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column() {
            infoList.forEach { info -> Text(text = info) }
        }
    }
}

@Preview
@Composable
fun StatsDisplayPreview() {
    StatsDisplay(
        listOf("Name", "HP", "MP")
    )
}