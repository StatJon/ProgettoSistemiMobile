package com.unibo.mobile.domain.model.ability

interface Ability {
    val abilityId: String
    val displayName: String
    val abilityType: AbilityType
    val targetType: TargetType
    val diceCount: Int
    val dice: Dice
    val mpCost: Int
}