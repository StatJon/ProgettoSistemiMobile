package com.unibo.mobile.progettosistemimobile

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.progettosistemimobile.ui.theme.ProgettoSistemiMobileTheme
import com.unibo.mobile.uicompose.screens.endscreen.EndScreen
import com.unibo.mobile.uicompose.screens.loadingscreen.LoadingScreen
import com.unibo.mobile.uicompose.screens.mainmenu.MainMenuScreen
import com.unibo.mobile.uicompose.screens.roomcombat.RoomCombatScreen
import com.unibo.mobile.uicompose.screens.roomsafe.RoomSafeScreen
import com.unibo.mobile.uicompose.viewmodel.GameState
import com.unibo.mobile.uicompose.viewmodel.GameViewModel
import com.unibo.mobile.uicompose.viewmodel.GameViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as DungeonGame
        setContent {
            ProgettoSistemiMobileTheme {
                val viewModel: GameViewModel = viewModel(
                    factory = GameViewModelFactory(app.useCasesProvider)
                )
                val state by viewModel.state.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameContent(
                        state = state,
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun GameContent(
    state: GameState,
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    when (state) {
        is GameState.MainMenu -> MainMenuScreen(
            isLandscape = isLandscape,
            winCounter = state.winCounter,
            playerClasses = state.playerClasses,
            onNewGameElementTap = viewModel::onNewGame,
            isContinuePossible = state.isContinuePossible,
            onContinueTap = viewModel::onContinue,
            onOptionsTap = viewModel::onOptions,
            modifier = modifier
        )
        is GameState.Loading -> LoadingScreen()
        is GameState.RoomSafe -> RoomSafeScreen(
            isLandscape = isLandscape,
            player = state.player,
            availableActions = state.availableActions,
            onRoomActionTap = viewModel::onRoomAction,
            onEnterNextRoomTap = viewModel::onEnterNextRoom,
            modifier = modifier
        )
        is GameState.RoomCombat -> {
            val ally = state.combatState.getAllAllies().first()
            RoomCombatScreen(
                isLandscape = isLandscape,
                combatState = state.combatState,
                abilityList = ally.abilities,
                player = ally,
                onAbilityAndEntityConfirmed = viewModel::onAbilityAndEntityConfirmed,
                modifier = modifier
            )
        }
        is GameState.EndScreen -> EndScreen(
            isWon = state.isWon,
            onBackToMenuTap = viewModel::onBackToMenu,
            isLandscape = isLandscape,
            modifier = modifier
        )
    }
}
