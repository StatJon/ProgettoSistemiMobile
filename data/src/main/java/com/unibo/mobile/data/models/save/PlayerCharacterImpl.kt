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
    override val abilityList: List<Ability>
) : PlayerCharacter {
}