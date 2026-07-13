package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.strategy.LevelUpStrategy

class LevelUpStrategyImpl : LevelUpStrategy {
    override fun applyLevelUp(
        playerCharacter: PlayerCharacter,
        newAbilities: List<Ability>,
        addMaxHp: Int,
        addMaxMp: Int,
        addMaxAp: Int
    ): PlayerCharacter {
        TODO("Not yet implemented")
    }
}