package com.unibo.mobile.domain.models

data class CombatSnapshot(
    val player: CharacterPlayer,
    val enemy: CharacterEnemy,
    val isPlayerTurn: Boolean,
    val isOver: Boolean,
    val combatStatus: CombatStatus
)