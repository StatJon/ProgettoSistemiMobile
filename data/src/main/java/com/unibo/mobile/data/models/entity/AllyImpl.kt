package com.unibo.mobile.data.models.entity

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
    override val strength: Int,
    override val dexterity: Int,
    override val constitution: Int,
    override val intelligence: Int,
    override val wisdom: Int,
    override val charisma: Int,
    override val abilities: List<Ability>
) : Ally {
    override fun getCurrentLevel(): Int {
        return (playerExp / 100) + 1
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