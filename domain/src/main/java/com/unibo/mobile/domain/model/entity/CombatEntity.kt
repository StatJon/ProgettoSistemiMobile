package com.unibo.mobile.domain.model.entity

import com.unibo.mobile.domain.model.ability.Ability

interface CombatEntity {
    val name: String
    val currentHp: Int
    val maxHp: Int
    val currentAp: Int
    val maxAp: Int
    val armorClass: Int
    val initiative: Int
    val abilities: List<Ability>

    fun changeHp(amount: Int): CombatEntity
    fun isTurnOver(): Boolean
    fun isAlive(): Boolean
}
