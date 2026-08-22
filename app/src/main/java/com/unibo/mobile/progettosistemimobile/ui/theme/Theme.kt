package com.unibo.mobile.progettosistemimobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DungeonColorScheme = darkColorScheme(
    primary = BrickRed,
    onPrimary = WarmWhite,
    secondary = StoneGrey,
    onSecondary = WarmWhite,
    tertiary = Terracotta,
    onTertiary = DungeonBackground,
    background = DungeonBackground,
    onBackground = ParchmentText,
    surface = DungeonSurface,
    onSurface = ParchmentText
)

@Composable
fun ProgettoSistemiMobileTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DungeonColorScheme,
        typography = Typography,
        content = content
    )
}