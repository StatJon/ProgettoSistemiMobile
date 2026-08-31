package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.unibo.mobile.uicompose.components.common.UiConstants

/**
 * Renders a 2D sprite image with fixed size and animation support via offset.
 *
 * @param painter Painter for the sprite image
 * @param contentName Content description for accessibility
 * @param modifier Modifier to apply to the Image
 */
@Composable
fun Sprite2D(
    painter: Painter,
    contentName: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = contentName,
        contentScale = ContentScale.Fit,
        modifier = modifier.size(UiConstants.SPRITE_SIZE, UiConstants.SPRITE_SIZE)
    )
}