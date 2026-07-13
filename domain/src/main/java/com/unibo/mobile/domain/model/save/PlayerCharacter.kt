package com.unibo.mobile.domain.model.save

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.PlayerClass

interface PlayerCharacter {
    val characterClass: PlayerClass
    val characterHp: Int
    val characterMaxHp: Int
    val characterMp: Int
    val characterMaxMp: Int
    val characterExp: Int
    val abilityList: List<Ability>
}
