package com.unibo.mobile.domain.repositories

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.PlayerClass

interface GamedataRepository {
    fun getPlayerClassByName(className: String): PlayerClass?
    fun getAllPlayerClasses(): List<PlayerClass>
    fun getDungeonBaseLength(): Int

    fun getFallBackLevelUpAbility() : Ability
}
