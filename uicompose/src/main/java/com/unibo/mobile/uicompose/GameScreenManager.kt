package com.unibo.mobile.uicompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.unibo.mobile.uicompose.components.common.UiConstants
import com.unibo.mobile.uicompose.screens.EndScreen
import com.unibo.mobile.uicompose.screens.GameScreen
import com.unibo.mobile.uicompose.screens.MainMenu

/**
 * Contains the UI and manages navigation of the Screens to show
 */
@Composable
fun GameScreenManager() {
    //---Apre la prima Screen (.MENU = iniziale)
    var currentScreen by rememberSaveable { mutableStateOf(ScreenStatus.MENU) }

    //--- Contenitore generale Ui
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(UiConstants.EDGE_BASE_PADDING)
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            //--- Selettore Screen
            when (currentScreen) {
                ScreenStatus.MENU -> MainMenu()
                ScreenStatus.GAME -> GameScreen()
                ScreenStatus.END_SCREEN -> EndScreen()
            }
        }
    }
}


enum class ScreenStatus {
    MENU,
    GAME,
    END_SCREEN
}