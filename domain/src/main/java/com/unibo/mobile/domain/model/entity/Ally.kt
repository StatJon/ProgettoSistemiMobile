package com.unibo.mobile.domain.model.entity

import com.unibo.mobile.domain.model.ability.Ability

abstract class Ally(
    override val name: String,
    override val currentHp: Int,
    override val maxHp: Int,
    override val currentAp: Int,
    override val maxAp: Int,
    override val armorClass: Int,
    override val initiative: Int,
    override val abilities: List<Ability>,
    val currentMp: Int,
    val maxMp: Int,
    val playerClass: PlayerClass,
    val playerExp: Int
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

    fun getCurrentLevel(): Int {
        TODO()
    }

    fun changeMp(amount: Int): Ally {
        TODO()
    }

}
