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

/**
 * Renders a 2D sprite image with fixed size and animation support via offset.
 *
 * @param painter Painter for the sprite image
 * @param contentName Content description for accessibility
 * @param modifier Modifier to apply to the Image
 * @param offsetForAnimation Offset for sprite animation positioning
 */
@Composable
fun Sprite2D(
    painter: Painter,
    contentName: String,
    modifier: Modifier = Modifier,
    //offsetForAnimation: Offset
) {
    Image(
        painter = painter,
        contentDescription = contentName,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .size(GameScreenConstants.SPRITE_SIZE, GameScreenConstants.SPRITE_SIZE)
            //.offset(offsetForAnimation.x.dp, offsetForAnimation.y.dp)
    )
}