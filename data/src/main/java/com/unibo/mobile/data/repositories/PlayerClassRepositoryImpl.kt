package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.gamedata.PlayerGamedata
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.repositories.PlayerClassRepository

class PlayerClassRepositoryImpl : PlayerClassRepository {
    override suspend fun getPlayerClassByName(className: String): PlayerClass? {
        return PlayerGamedata.PlayerClassList.find { it.className == className }
    }

    override suspend fun getAllPlayerClasses(): List<PlayerClass> {
        return PlayerGamedata.PlayerClassList
    }
}