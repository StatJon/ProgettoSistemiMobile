package com.unibo.mobile.domain.model.save

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.PlayerClass

interface PlayerCharacter {
    val characterClass: PlayerClass
    val characterHp: Int
    val characterMaxHp: Int
    val characterMp: Int
    val characterMaxMp: Int
    val characterLevel: Int
    val characterExp: Int
    val abilityList: List<Ability>

    fun changeHp(amount: Int): PlayerCharacter
    fun changeMp(amount: Int): PlayerCharacter
    fun addMaxHp(amount: Int): PlayerCharacter
    fun addMaxMp(amount: Int): PlayerCharacter
    fun addAbilities(abilityList: List<Ability>): PlayerCharacter
    fun setLevel(newLevel: Int) : PlayerCharacter
    fun addExp(amount: Int): PlayerCharacter
}
