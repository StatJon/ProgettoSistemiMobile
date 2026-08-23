package com.unibo.mobile.domain.models

data class PlayerClass(
    val name: String,
    val className: String,
    val unlockCounter: Int,
    val baseHealthPoints: Int,
    val baseManaPoints: Int,
    val baseArmorClass: Int,
    val baseAttackBonus: Int,
    val healthGrowth: Int,
    val manaGrowth: Int,
    val baseAbilityList: List<Ability>
)
