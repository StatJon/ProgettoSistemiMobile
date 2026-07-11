package com.unibo.mobile.domain.model.entity

import com.unibo.mobile.domain.model.ability.Ability

abstract class Enemy (
    override val name: String,
    override val currentHp: Int,
    override val maxHp: Int,
    override val currentAp: Int,
    override val maxAp: Int,
    override val armorClass: Int,
    override val initiative: Int,
    override val abilities: List<Ability>,
    val enemyType: EnemyType,
    val challengeRating: Float
    ) : CombatEntity {
    override fun changeHp(amount: Int): CombatEntity {
        TODO("Not yet implemented")
    }

    override fun isTurnOver(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAlive(): Boolean {
        TODO("Not yet implemented")
    }
}
