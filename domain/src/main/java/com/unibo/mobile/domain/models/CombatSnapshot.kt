package com.unibo.mobile.domain.models

data class CombatSnapshot(
    val player: CharacterPlayer,
    val enemy: CharacterEnemy,
    val combatStatus: CombatStatus,
    val lastAbilityResult: AbilityResult?
)