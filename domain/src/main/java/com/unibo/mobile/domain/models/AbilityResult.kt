package com.unibo.mobile.domain.models

data class AbilityResult(
    val target: Character,
    val amount: Int,
    val ability: Ability,
    val diceResult: Int
)