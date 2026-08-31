package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unibo.mobile.domain.di.UseCaseProvider
import com.unibo.mobile.uicompose.R
import com.unibo.mobile.uicompose.components.gamescreen.GameView
import com.unibo.mobile.uicompose.components.gamescreen.PlayerControls
import com.unibo.mobile.uicompose.viewmodel.GameScreenViewModel
import com.unibo.mobile.uicompose.viewmodel.GameScreenViewModelFactory


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onNavigateToMenu: () -> Unit,
    onNavigateToEndScreen: (Boolean) -> Unit,
    viewModel: GameScreenViewModel = viewModel(
        factory = GameScreenViewModelFactory(
            applyAbilityResultUseCase = UseCaseProvider.applyAbilityResultUseCase,
            applyPlayerAbilityCostUseCase = UseCaseProvider.applyPlayerAbilityCostUseCase,
            calculateAbilityResultUseCase = UseCaseProvider.calculateAbilityResultUseCase,
            checkCombatStatusUseCase = UseCaseProvider.checkCombatStatusUseCase,
            decideEnemyAbilityUseCase = UseCaseProvider.decideEnemyAbilityUseCase,
            determineChallengeRatingUseCase = UseCaseProvider.determineChallengeRatingUseCase,
            determineGamePhaseUseCase = UseCaseProvider.determineGamePhaseUseCase,
            fetchEnemyByChallengeRatingUseCase = UseCaseProvider.fetchEnemyByChallengeRatingUseCase,
            loadSaveGameUseCase = UseCaseProvider.loadSaveGameUseCase,
            saveSaveGameUseCase = UseCaseProvider.saveSaveGameUseCase,
            levelUpUseCase = UseCaseProvider.levelUpUseCase,
            checkpointUseCase = UseCaseProvider.checkpointUseCase,
            validateDungeonLengthUseCase = UseCaseProvider.validateDungeonLengthUseCase,
            executeTurnUseCase = UseCaseProvider.executeTurnUseCase
        )
    )
) {
    println("DEBUG: Accessing GameScreen")

    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    val lockUI = viewModel.lockUi.collectAsStateWithLifecycle()
    val combatSnapshot = viewModel.combatSnapshot.collectAsStateWithLifecycle()
    val gamePhase = viewModel.gamePhase.collectAsStateWithLifecycle()
    val characterPlayer = viewModel.characterPlayer.collectAsStateWithLifecycle()
    val navigateToEndScreen = viewModel.navigateToEndScreen.collectAsStateWithLifecycle()
    val dungeonIndex = viewModel.dungeonIndex.collectAsStateWithLifecycle()
    val dungeonLength = viewModel.dungeonLength.collectAsStateWithLifecycle()
    val isWon = navigateToEndScreen.value

    if (isWon != null) {
        LaunchedEffect(Unit) {
            onNavigateToEndScreen(isWon)
            viewModel.resetNavigateToEndScreen()
        }
    }

    // --- GameScreen UI
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GameView(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            combatSnapshot = combatSnapshot.value,
            gamePhase = gamePhase.value
        )
        Text(
            text = stringResource(R.string.dungeon_counter) + (dungeonIndex.value+1) + " / " + (dungeonLength.value+1),
            modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
        )
        PlayerControls(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(),
            characterPlayer = characterPlayer.value,
            gamePhase = gamePhase.value,
            lockUi = lockUI.value,
            onAbilitySelected = viewModel::onAbilitySelected
        )
    }
    if (isLoading.value) {
        LoadingScreen()
    }
}

// --- --- --- --- --- --- --- --- --- --- //
//----- Preview -----//

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen(
        onNavigateToMenu = {},
        onNavigateToEndScreen = {}
    )
}