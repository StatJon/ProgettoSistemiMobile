package com.unibo.mobile.domain.models

sealed interface Ability {
    val name: String
    val level: Int
    val isAoe: Boolean
    val actionCost: ActionCost
    val manaCost: Int
    val dicesToRoll: DicesToRoll
    val requiresHitRoll : Boolean
}