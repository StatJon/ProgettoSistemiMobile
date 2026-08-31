package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.foundation.layout.Arrangement
import com.unibo.mobile.uicompose.R
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview


/**
 * Character visualizer, combines a Sprite2D and StatsDisplay in a vertical layout.
 *
 * @param spritePainter Painter for the sprite image
 * @param spriteName Content description for accessibility
 * @param infoList List of stat strings to display
 * @param modifier Modifier to apply to the Column container
 * //@param spriteOffset Offset for sprite animation positioning, default (0,0)
 */
@Composable
fun SpriteWithStats(
    spritePainter: Painter,
    spriteName: String,
    infoList: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Sprite2D(spritePainter, spriteName)
        StatsDisplay(infoList)
    }
}

@Preview
@Composable
fun SpriteWithStatsPreview() {
    SpriteWithStats(
        spritePainter = painterResource(R.drawable.cleric),
        spriteName = "Cleric",
        infoList = listOf("Name", "Hp", "Mp")
    )
}