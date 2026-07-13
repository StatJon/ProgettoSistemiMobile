package com.unibo.mobile.domain.model.entity

import com.unibo.mobile.domain.model.ability.Ability

interface CombatEntity {
    val entityId: String
    val displayName: String
    val currentHp: Int
    val maxHp: Int
    val currentAp: Int
    val maxAp: Int
    val armorClass: Int
    val initiative: Int
    val abilities: List<Ability>
    fun isTurnOver(): Boolean = currentAp <= 0
    fun isAlive(): Boolean = currentAp > 0

    fun resetAp(): CombatEntity = changeAp(maxAp)
    fun changeHp(amount: Int): CombatEntity
    fun changeAp(amount: Int): CombatEntity

}
