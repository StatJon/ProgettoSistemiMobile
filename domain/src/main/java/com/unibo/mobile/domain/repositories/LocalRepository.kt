package com.unibo.mobile.domain.repositories

import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.entity.EnemyType
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.SaveGame

interface LocalRepository {
    suspend fun getSaveGame(): SaveGame?
    suspend fun saveSaveGame(saveGame: SaveGame): Void
    fun getDungeonPreset(winCounter: Int): Dungeon
    fun getPlayerClasses(): List<PlayerClass>
    fun getEnemyTypes(): List<EnemyType>
}