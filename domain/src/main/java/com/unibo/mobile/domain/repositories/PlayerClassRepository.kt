package com.unibo.mobile.domain.repositories

import com.unibo.mobile.domain.models.PlayerClass

interface PlayerClassRepository {
    suspend fun getPlayerClassByName(className: String): PlayerClass?
    suspend fun getAllPlayerClasses(): List<PlayerClass>
}