package com.unibo.mobile.domain.models

data class AbilityHeal(
    override val name: String,
    override val level: Int,
    override val isAoe: Boolean,
    override val actionCost: ActionCost,
    override val manaCost: Int,
    val healDicesToRoll: DicesToRoll,
    ) : Ability
