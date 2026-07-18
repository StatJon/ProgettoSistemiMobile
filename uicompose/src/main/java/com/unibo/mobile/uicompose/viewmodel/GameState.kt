package com.unibo.mobile.uicompose.viewmodel

import com.unibo.mobile.domain.model.combat.CombatState
import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.PlayerCharacter

sealed class GameState {

    object Loading: GameState()
    data class MainMenu(
        val winCounter: Int,
        val playerClasses: List<PlayerClass>,
        val isContinuePossible: Boolean
    ) : GameState()

    data class RoomSafe(
        val player: PlayerCharacter,
        val availableActions: List<RoomAction>
    ) : GameState()

    data class RoomCombat(
        val combatState: CombatState,
        val playerCharacter: PlayerCharacter,
    ) : GameState()

    data class EndScreen(
        val isWon: Boolean
    ) : GameState()

}