package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.models.EnemyTypeImpl
import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.entity.EnemyType
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.repositories.LocalRepository

class LocalRepositoryImpl : LocalRepository {

    override suspend fun getSaveGame(): SaveGame? {
        TODO("Not yet implemented")
    }

    override suspend fun saveSaveGame(saveGame: SaveGame) {
        TODO("Not yet implemented")
    }

    override fun getDungeonPreset(winCounter: Int): Dungeon {
        TODO("Not yet implemented")
    }

    override fun getPlayerClasses(): List<PlayerClass> {
        TODO("Not yet implemented")
    }

    private val enemyTypeList : List <EnemyType> = listOf(
    EnemyTypeImpl(enemyTypeId = "humanoid", displayName = "enemy_type_humanoid"),
    EnemyTypeImpl(enemyTypeId = "beast", displayName = "enemy_type_beast"),
    EnemyTypeImpl(enemyTypeId = "undead", displayName = "enemy_type_undead"), EnemyTypeImpl(enemyTypeId = "dragon", displayName = "enemy_type_dragon"),
    EnemyTypeImpl(enemyTypeId = "fiend", displayName = "enemy_type_fiend"),
    EnemyTypeImpl(enemyTypeId = "elemental", displayName = "enemy_type_elemental"),
    EnemyTypeImpl(enemyTypeId = "construct", displayName = "enemy_type_construct"),
    EnemyTypeImpl(enemyTypeId = "aberration", displayName = "enemy_type_aberration"),
    EnemyTypeImpl(enemyTypeId = "giant", displayName = "enemy_type_giant"),
    EnemyTypeImpl(enemyTypeId = "fey", displayName = "enemy_type_fey"),
    EnemyTypeImpl(enemyTypeId = "monstrosity", displayName = "enemy_type_monstrosity"),
    EnemyTypeImpl(enemyTypeId = "ooze", displayName = "enemy_type_ooze"),
    EnemyTypeImpl(enemyTypeId = "plant", displayName = "enemy_type_plant"),
    EnemyTypeImpl(enemyTypeId = "celestial", displayName = "enemy_type_celestial")
    )

    override fun getEnemyTypes(): List<EnemyType> {
        return enemyTypeList
    }
}