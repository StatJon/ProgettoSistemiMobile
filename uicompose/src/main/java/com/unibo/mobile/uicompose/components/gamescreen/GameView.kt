package com.unibo.mobile.uicompose.components.gamescreen

import com.unibo.mobile.uicompose.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

//TODO Aggiungere VIEWMODEL e cablare correttamente, sostituire i placeholder
@Composable
fun GameView(
    modifier: Modifier = Modifier,
) {
    // --- General Container
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.dungeon_background),
            contentDescription = stringResource(R.string.background),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SpriteWithStats(
                spritePainter = painterResource(R.drawable.cleric),
                spriteName = "Cleric",
                infoList = listOf("Name", "HP", "MP"),

                )
            SpriteWithStats(
                spritePainter = painterResource(R.drawable.humanoid),
                spriteName = "Humanoid",
                infoList = listOf("Name", "HP", "MP"),

                )
        }

    }
}

@Preview
@Composable
fun GameViewPreview() {
    GameView(modifier = Modifier)
}