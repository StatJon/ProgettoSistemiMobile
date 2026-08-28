package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.gamedata.Gamedata
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.repositories.GamedataRepository

class GamedataRepositoryImpl : GamedataRepository {
    override fun getPlayerClassByName(className: String): PlayerClass? {
        return Gamedata.PlayerClassList.find { it.className == className }
    }

    override fun getAllPlayerClasses(): List<PlayerClass> {
        return Gamedata.PlayerClassList
    }

    override fun getDungeonBaseLength(): Int {
        return Gamedata.baseDungeonLength
    }

    override fun getFallBackLevelUpAbility(): Ability {
        return Gamedata.fallBackLevelUpAbility
    }


}