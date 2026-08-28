package com.unibo.mobile.uicompose.components.gamescreen

import com.unibo.mobile.uicompose.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.domain.models.CombatSnapshot
import com.unibo.mobile.domain.models.GamePhase

//TODO Aggiungere VIEWMODEL e cablare correttamente, sostituire i placeholder
@Composable
fun GameView(
    modifier: Modifier = Modifier,
    combatSnapshot: CombatSnapshot?,
    gamePhase: GamePhase
) {
    // --- General Container
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.dungeon_background),
            contentDescription = stringResource(R.string.background),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (gamePhase == GamePhase.COMBAT && combatSnapshot != null) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SpriteWithStatsForCharacter(
                    character = combatSnapshot.player,
                    modifier = Modifier.weight(1f)
                )
                SpriteWithStatsForCharacter(
                    character = combatSnapshot.enemy,
                    modifier = Modifier.weight(1f)
                )
            }
        } else if (gamePhase == GamePhase.CHECKPOINT) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                SpriteWithStatsForCharacter(
                    character = combatSnapshot?.player ?: return@Box,
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview
@Composable
fun GameViewPreview() {
    GameView(
        combatSnapshot = null,
        gamePhase = GamePhase.COMBAT,
        modifier = Modifier
    )
}