package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    if (gamePhase == GamePhase.COMBAT) {
        LazyColumn(modifier = modifier.fillMaxWidth().fillMaxHeight()) {
            items(characterPlayer?.characterData?.abilityList ?: emptyList()) { ability ->
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