package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unibo.mobile.domain.di.UseCaseProvider
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.uicompose.R
import com.unibo.mobile.uicompose.components.common.UiConstants
import com.unibo.mobile.uicompose.viewmodel.MainMenuViewModel
import com.unibo.mobile.uicompose.viewmodel.MainMenuViewModelFactory

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    onNavigateToGame: () -> Unit,
    viewModel: MainMenuViewModel = viewModel(
        factory = MainMenuViewModelFactory(
            getAllPlayerClassesUseCase = UseCaseProvider.getAllPlayerClassesUseCase,
            loadSaveGameUseCase = UseCaseProvider.loadSaveGameUseCase
        )
    )
) {
    // --- Recupero variabili da ViewModel
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    val playerClassesList = viewModel.playerClassesList.collectAsStateWithLifecycle()
    val saveGame = viewModel.saveGame.collectAsStateWithLifecycle()
    val winCounter = saveGame.value.winCounter

    // --- LoadingScreen Check
    if (isLoading.value) {
        LoadingScreen()
    } else {

        println("Accessing Main Menu")
        println("SaveGame.winCounter: $winCounter")
        // --- MainMenu UI
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(UiConstants.EDGE_BASE_PADDING),
            verticalArrangement = Arrangement.spacedBy(UiConstants.SECTION_SPACING),
        ) {
            // --- Title
            Text(
                text = stringResource(R.string.main_menu_title),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                textAlign = TextAlign.Center,

            )
            // --- WinCounter
            Text(
                text = stringResource(R.string.dungeons_won) + winCounter,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f),
                textAlign = TextAlign.Center
            )
            // --- NewGame Buttons with Classes
            NewGameButtonsWithClasses(
                playerClassesList.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            )
            // --- Continue
            Button(
                onClick = onNavigateToGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(UiConstants.BUTTON_HEIGHT)
            ) {
                Text(text = stringResource(R.string.continue_button))
            }
        }
    }
    println("Completed MainMenu Construction")
}

// --- --- --- --- --- --- --- --- --- --- //
// --- Composables --- //
@Composable
private fun NewGameButtonsWithClasses(
    playerClasses: List<PlayerClass>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(UiConstants.SECTION_SPACING)
    ) {
        items(playerClasses) { playerClass ->
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(UiConstants.BUTTON_HEIGHT)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.new_game_button),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = playerClass.name,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// --- --- --- --- --- --- --- --- --- --- //
//----- Preview -----//
/*
@Preview(showBackground = true)
@Composable
private fun MainMenuPreview() {
    val mockViewModel = remember { mainMenuViewModelMock() }
    MainMenu(viewModel = mockViewModel)
}

private fun mainMenuViewModelMock(): MainMenuViewModel {
    val mockPlayerUseCase = GetAllPlayerClassesUseCaseMock()
    return MainMenuViewModel(
        mockPlayerUseCase,
        loadSaveGameUseCase = mockSaveUseCase
    )
}


private class GetAllPlayerClassesUseCaseMock : GetAllPlayerClassesUseCase {
    override suspend fun invoke(): List<PlayerClass> {
        return listOf(
            PlayerClass(
                name = "Suor Mazzate",
                className = "cleric",
                unlockCounter = 0,
                baseHealthPoints = 12,
                baseManaPoints = 4,
                baseArmorClass = 14,
                baseAttackBonus = 2,
                healthGrowth = 8,
                manaGrowth = 2
            ),
            PlayerClass(
                name = "William",
                className = "sorcerer",
                unlockCounter = 2,
                baseHealthPoints = 10,
                baseManaPoints = 6,
                baseArmorClass = 14,
                baseAttackBonus = 2,
                healthGrowth = 6,
                manaGrowth = 4
            )
        )

    }
}

private class mockSaveGame : SaveGame(1,mockSaveSession) {

}

private class mockSaveSession : SaveSession() {

}

private class LoadSaveGameUseCaseMock : LoadSaveGameUseCase {
    override suspend fun invoke(): SaveGame {
        TODO("Not yet implemented")
    }

}
*/