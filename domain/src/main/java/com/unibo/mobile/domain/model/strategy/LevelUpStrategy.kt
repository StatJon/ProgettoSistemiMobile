package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.save.PlayerCharacter

interface LevelUpStrategy {
    fun applyLevelUp(
        playerCharacter: PlayerCharacter,
        newAbilities: List<Ability>,
    ): PlayerCharacter
}