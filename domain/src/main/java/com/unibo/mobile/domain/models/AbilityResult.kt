package com.unibo.mobile.domain.models

data class AbilityResult(
    val target: Character,
    val ability: Ability,
    val effectDiceRoll: Int, // damage or heal amount
    val hitDiceRoll: Int
)