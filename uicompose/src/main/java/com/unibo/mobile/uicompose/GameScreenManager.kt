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
import com.unibo.mobile.uicompose.common.UiConstants
import com.unibo.mobile.uicompose.screens.MainMenu

/**
 * Contains the UI and manages navigation of the Screens to show
 */
@Composable
fun GameScreenManager() {
    //---Apre la prima Screen (.MENU = iniziale)
    var currentScreen by rememberSaveable { mutableStateOf(GameScreenStatus.MENU) }

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
                GameScreenStatus.MENU -> MainMenu()
                GameScreenStatus.GAME -> TODO("Creare prima lo Screen")
                GameScreenStatus.END_SCREEN -> TODO("Creare prima lo Screen")
            }
        }
    }
}


enum class GameScreenStatus {
    MENU,
    GAME,
    END_SCREEN
}