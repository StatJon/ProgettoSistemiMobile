package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.data.gamedata.LocalGameData
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.strategy.LevelUpStrategy

class LevelUpStrategyDefault : LevelUpStrategy {

    override fun isAbleToLevelUp(playerCharacter: PlayerCharacter): Boolean {
        val calculatedLevel = (playerCharacter.characterExp / LocalGameData.EXP_PER_LEVEL) + 1
        return calculatedLevel > playerCharacter.characterLevel
    }

    override fun applyLevelUp(
        playerCharacter: PlayerCharacter,
        newAbilities: List<Ability>
    ): PlayerCharacter {
        val hpIncrease = playerCharacter.characterClass.constitution / 2
        val mpIncrease = playerCharacter.characterClass.intelligence / 2
        val newLevel = (playerCharacter.characterExp / LocalGameData.EXP_PER_LEVEL) + 1
        return playerCharacter
            .addMaxHp(hpIncrease)
            .addMaxMp(mpIncrease)
            .addAbilities(newAbilities)
            .setLevel(newLevel)
    }
}