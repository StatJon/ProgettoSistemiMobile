package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.GamePhase

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    characterPlayer: CharacterPlayer?,
    gamePhase: GamePhase,
    lockUi: Boolean,
    onAbilitySelected: (Ability) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (gamePhase == GamePhase.COMBAT) {
            characterPlayer?.characterData?.abilityList?.forEach { ability ->
                ActionButton(
                    ability = ability,
                    lockUi = lockUi,
                    onClick = {
                        println("DEBUG: Button clicked: ${ability.name}")
                        onAbilitySelected(ability)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else if (gamePhase == GamePhase.CHECKPOINT) {
            Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Continue")
            }
        }
    }
}

@Preview
@Composable
fun PlayerControlsPreview() {
    PlayerControls(
        characterPlayer = null,
        gamePhase = GamePhase.COMBAT,
        onAbilitySelected = {},
        lockUi = false,
    )
}