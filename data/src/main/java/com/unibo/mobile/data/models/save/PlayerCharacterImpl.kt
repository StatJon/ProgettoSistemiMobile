package com.unibo.mobile.data.models.save

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.PlayerCharacter

data class PlayerCharacterImpl(
    override val characterClass: PlayerClass,
    override val characterHp: Int,
    override val characterMaxHp: Int,
    override val characterMp: Int,
    override val characterMaxMp: Int,
    override val characterExp: Int,
    override val abilityList: List<Ability>,
) : PlayerCharacter {
    override fun changeHp(amount: Int): PlayerCharacter {
        return this.copy(characterHp = characterHp + amount)
    }

    override fun changeMp(amount: Int): PlayerCharacter {
        return this.copy(characterMp = characterMp + amount)
    }

    override fun addMaxHp(amount: Int): PlayerCharacter {
        return this.copy(characterMaxHp = characterMaxHp + amount)
    }

    override fun addMaxMp(amount: Int): PlayerCharacter {
        return this.copy(characterMaxMp = characterMaxMp + amount)
    }

    override fun addAbilities(abilityList: List<Ability>): PlayerCharacter {
        return this.copy(abilityList = this.abilityList + abilityList)
    }

    override fun addExp(amount: Int): PlayerCharacter {
        return this.copy(characterExp = characterExp + amount)
    }
}