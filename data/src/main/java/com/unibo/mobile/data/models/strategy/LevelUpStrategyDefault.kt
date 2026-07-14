package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.strategy.LevelUpStrategy

class LevelUpStrategyDefault : LevelUpStrategy {
    override fun applyLevelUp(
        playerCharacter: PlayerCharacter,
        newAbilities: List<Ability>
    ): PlayerCharacter {
        val hpIncrease = playerCharacter.characterClass.constitution / 2
        val mpIncrease = playerCharacter.characterClass.intelligence / 2

        return playerCharacter
            .addMaxHp(hpIncrease)
            .addMaxMp(mpIncrease)
            .addAbilities(newAbilities)
    }
}