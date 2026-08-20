package com.unibo.mobile.progettosistemimobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.unibo.mobile.progettosistemimobile.ui.theme.ProgettoSistemiMobileTheme
import com.unibo.mobile.uicompose.ScreenManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgettoSistemiMobileTheme {
                ScreenManager()
            }
        }
    }
}
