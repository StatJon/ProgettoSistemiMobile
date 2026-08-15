package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.uicompose.components.gamescreen.GameView
import com.unibo.mobile.uicompose.components.gamescreen.PlayerControls


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    //viewModel: GameScreenViewModel = viewModel(
    //    factory = GameScreenViewModelFactory(
    //        [...]UseCase = UseCaseProvider.[...]UseCase
    //    )
    //) TODO: SOSTITUIRE CON VIEWMODEL E FACTORY
) {
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
        modifier = modifier
            .fillMaxSize()
    ) {
        GameView(modifier = Modifier.weight(0.5f))
        PlayerControls(modifier = Modifier.weight(0.5f))
    }
}

// --- --- --- --- --- --- --- --- --- --- //
// --- Composables --- //


// --- --- --- --- --- --- --- --- --- --- //
//----- Preview -----//

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen()

}