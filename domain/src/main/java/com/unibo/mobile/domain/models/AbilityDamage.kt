package com.unibo.mobile.domain.models

data class AbilityDamage(
    override val name: String,
    override val level: Int,
    override val isAoe: Boolean,
    val damageDices: DicesToRoll
) : Ability
