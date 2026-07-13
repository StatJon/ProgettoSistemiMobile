package com.unibo.mobile.data.models

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.PlayerClass

data class AllyImpl(
    override val entityId: String,
    override val displayName: String,
    override val currentMp: Int,
    override val maxMp: Int,
    override val playerClass: PlayerClass,
    override val playerExp: Int,
    override val currentHp: Int,
    override val maxHp: Int,
    override val currentAp: Int,
    override val maxAp: Int,
    override val armorClass: Int,
    override val initiative: Int,
    override val abilities: List<Ability>,
) : Ally {
    override fun getCurrentLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun changeMp(amount: Int): Ally {
        return this.copy(currentMp = currentMp + amount)
    }

    override fun changeHp(amount: Int): Ally {
        return this.copy(currentHp = currentHp + amount)
    }

    override fun changeAp(amount: Int): Ally {
        return this.copy(currentAp = currentAp + amount)
    }
}