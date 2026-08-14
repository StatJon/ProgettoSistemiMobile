package com.unibo.mobile.uicompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.unibo.mobile.uicompose.screens.MainMenu

/**
 * Manages Navigation of the Screens to show
 */
@Composable
fun GameScreenSelector() {
    //Apre la prima Screen (.MENU = iniziale)
    var currentScreen by rememberSaveable { mutableStateOf(GameScreenStatus.MENU) }

    when (currentScreen) {
        GameScreenStatus.MENU -> MainMenu()
        GameScreenStatus.GAME -> TODO("Creare prima lo Screen")
        GameScreenStatus.END_SCREEN -> TODO("Creare prima lo Screen")
    }
}

enum class GameScreenStatus {
    MENU,
    GAME,
    END_SCREEN
}