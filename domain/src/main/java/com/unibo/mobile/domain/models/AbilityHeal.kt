package com.unibo.mobile.domain.models

data class AbilityHeal(
    override val name: String,
    override val level: Int,
    override val isAoe: Boolean,
    val healDicesToRoll: DicesToRoll
): Ability
