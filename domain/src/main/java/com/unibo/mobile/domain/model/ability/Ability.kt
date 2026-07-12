package com.unibo.mobile.domain.model.ability

interface Ability {
    val abilityId: String
    val displayName: String
    val actionCost: Int //new
    val level: Int //was mpCost
    val abilityType: AbilityType // val targetType: TargetType Obsolete, unified in AbilityType
    val diceCount: Int
    val diceType: Dice

}
