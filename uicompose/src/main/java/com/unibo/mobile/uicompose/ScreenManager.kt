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
fun ScreenManager() {
    //---Apre la prima Screen (.MENU = iniziale)
    var currentScreen by rememberSaveable { mutableStateOf(ScreenStatus.MENU) }
    var sessionResult: Boolean? by rememberSaveable { mutableStateOf(null) }

    //--- Contenitore generale Ui
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(UiConstants.EDGE_BASE_PADDING)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            //--- Selettore Screen
            when (currentScreen) {
                ScreenStatus.MENU -> MainMenu(
                    onNavigateToGame = {
                        currentScreen = ScreenStatus.GAME
                        sessionResult = null
                    },
                )

                ScreenStatus.GAME -> GameScreen(
                    onNavigateToMenu = { currentScreen = ScreenStatus.MENU },
                    onNavigateToEndScreen = { isWon ->
                        currentScreen = ScreenStatus.END_SCREEN
                        sessionResult = isWon
                    }
                )

                ScreenStatus.END_SCREEN -> EndScreen(
                    isWon = sessionResult ?: false,
                    onNavigateToMenu = { currentScreen = ScreenStatus.MENU }
                )
            }
        }
    }
}


enum class ScreenStatus {
    MENU,
    GAME,
    END_SCREEN
}