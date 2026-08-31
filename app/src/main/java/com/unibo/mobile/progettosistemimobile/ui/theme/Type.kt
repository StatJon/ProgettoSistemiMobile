package com.unibo.mobile.progettosistemimobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.unibo.mobile.progettosistemimobile.R

// Font in res/font/: medievalsharp.ttf, vt323.ttf
val MedievalSharp = FontFamily(Font(R.font.medievalsharp))
val VT323 = FontFamily(Font(R.font.vt323))

val Typography = Typography(
    // Titles
    displayLarge = TextStyle(
        fontFamily = MedievalSharp,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.sp
    ),
    // Secondary Titles
    displaySmall = TextStyle(
        fontFamily = MedievalSharp,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    // Section headers
    titleLarge = TextStyle(
        fontFamily = MedievalSharp,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // Main text (StatsDisplay)
    bodyLarge = TextStyle(
        fontFamily = VT323,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Caption, helper text (NOT IN USE)
    labelSmall = TextStyle(
        fontFamily = VT323,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    // Buttons
    labelLarge = TextStyle(
        fontFamily = VT323,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    )

)