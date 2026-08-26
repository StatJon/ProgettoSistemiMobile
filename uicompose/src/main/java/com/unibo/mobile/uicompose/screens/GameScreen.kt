package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unibo.mobile.domain.di.UseCaseProvider
import com.unibo.mobile.uicompose.components.gamescreen.GameView
import com.unibo.mobile.uicompose.components.gamescreen.PlayerControls
import com.unibo.mobile.uicompose.viewmodel.GameScreenViewModel
import com.unibo.mobile.uicompose.viewmodel.GameScreenViewModelFactory


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onNavigateToMenu: () -> Unit,
    onNavigateToEndScreen: () -> Unit, //TODO: Aggiungere boolean o simili per win/loss verso EndScreen
    viewModel: GameScreenViewModel = viewModel(
        factory = GameScreenViewModelFactory(
            calculateAbilityResultUseCase = UseCaseProvider.calculateAbilityResultUseCase,
            fetchEnemyByChallengeRatingUseCase = UseCaseProvider.fetchEnemyByChallengeRatingUseCase,
            fetchAbilityByNameUseCase = UseCaseProvider.fetchAbilityByNameUseCase,
            determineGamePhaseUseCase = UseCaseProvider.determineGamePhaseUseCase,
            determineChallengeRatingUseCase = UseCaseProvider.determineChallengeRatingUseCase,
            applyPlayerAbilityCostUseCase = UseCaseProvider.applyPlayerAbilityCostUseCase,
            checkCombatStatusUseCase = UseCaseProvider.checkCombatStatusUseCase,
            applyAbilityResultUseCase = UseCaseProvider.applyAbilityResultUseCase
        )
    )
) {
    println("Accessing GameScreen")
    /*
    // --- Recupero variabili da ViewModel
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()

    // --- LoadingScreen Check
    if (isLoading.value) {
        LoadingScreen()
    }
    */
    // --- GameScreen UI
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        //TODO Aggiungere Logica per decidere il contenuto Combat/Safe
        GameView(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth())
        PlayerControls(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth())
    }
}

// --- --- --- --- --- --- --- --- --- --- //
//----- Preview -----//

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen(
        onNavigateToMenu = TODO(),
        onNavigateToEndScreen = TODO()
    )

}