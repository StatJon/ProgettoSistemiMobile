package com.unibo.mobile.uicompose.screens.mainmenu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.unibo.mobile.uicompose.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.uicompose.common.LargeButton
import com.unibo.mobile.uicompose.common.LargeTitle
import com.unibo.mobile.uicompose.common.ScreenLayoutStandard

@Composable
fun MainMenuScreen(
    isLandscape: Boolean,
    winCounter: Int,
    playerClasses: List<PlayerClass>,
    onNewGameElementTap: (PlayerClass) -> Unit,
    isContinuePossible: Boolean,
    onContinueTap: () -> Unit,
    onOptionsTap: () -> Unit
) {
    ScreenLayoutStandard(
        isLandscape = isLandscape,
        displayContent = { DisplayContent(winCounter) },
        inputContent = {
            InputContent(
                playerClasses = playerClasses,
                winCounter = winCounter,
                onNewGameElementTap = onNewGameElementTap,
                isContinuePossible = isContinuePossible,
                onContinueTap = onContinueTap,
                onOptionsTap = onOptionsTap
            )
        }
    )
}

// --- Composables --- //

@Composable
private fun DisplayContent(
    winCounter: Int
) {
    LargeTitle(stringResource(R.string.main_menu_title))
    Text(text = stringResource(R.string.dungeons_won, winCounter))

}

@Composable
private fun InputContent(
    playerClasses: List<PlayerClass>,
    winCounter: Int,
    onNewGameElementTap: (PlayerClass) -> Unit,
    isContinuePossible: Boolean,
    onContinueTap: () -> Unit,
    onOptionsTap: () -> Unit
) {
    NewGameButtons(playerClasses, winCounter, onNewGameElementTap)
    OptionsButton(onTap = onOptionsTap)
    ContinueButton(isContinuePossible, onContinueTap)


}

@Composable
private fun NewGameButtons(
    playerClasses: List<PlayerClass>,
    winCounter: Int,
    onTap: (PlayerClass) -> Unit
) {
    playerClasses.forEach { playerClass ->
        LargeButton(
            text = stringResource(R.string.new_game_button, playerClass.displayName),
            enabled = winCounter >= playerClass.unlockCountRequired,
            onTap = { onTap(playerClass) }
        )
    }
}

@Composable
private fun OptionsButton(
    onTap: () -> Unit
) {
    LargeButton(
        text = stringResource(R.string.options_button),
        onTap = onTap
    )
}

@Composable
private fun ContinueButton(
    enabled: Boolean,
    onTap: () -> Unit
) {
    LargeButton(
        text = stringResource(R.string.continue_button),
        onTap = onTap,
        enabled = enabled
    )
}
// --- Composables --- //

// --- Preview --- //
@Preview(showBackground = true)
@Composable
private fun MainMenuScreenPreview() {
    val fakeClasses = listOf(

        object : PlayerClass {
            override val classId = "cleric"
            override val displayName = "Cleric"
            override val unlockCountRequired = 0
            override val strength = 10
            override val dexterity = 14
            override val constitution = 16
            override val intelligence = 8
            override val wisdom = 16
            override val charisma = 8
        },
        object : PlayerClass {
            override val classId = "druid"
            override val displayName = "Druid"
            override val unlockCountRequired = 1
            override val strength = 8
            override val dexterity = 14
            override val constitution = 14
            override val intelligence = 10
            override val wisdom = 16
            override val charisma = 10
        }
    )


    MainMenuScreen(
        isLandscape = false,
        winCounter = 0,
        playerClasses = fakeClasses,
        onNewGameElementTap = {},
        isContinuePossible = true,
        onContinueTap = {},
        onOptionsTap = {}
    )
}
// --- Preview --- //