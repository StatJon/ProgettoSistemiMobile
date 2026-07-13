package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.db.AppDatabase
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.data.models.entity.EnemyTypeImpl
import com.unibo.mobile.data.models.entity.PlayerClassImpl
import com.unibo.mobile.data.models.save.PlayerCharacterImpl
import com.unibo.mobile.data.models.save.SaveGameImpl
import com.unibo.mobile.data.models.save.SaveSessionImpl
import com.unibo.mobile.data.models.save.UserSettingsImpl
import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.entity.EnemyType
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.repositories.LocalRepository

class LocalRepositoryImpl (
    private val saveGameDao: SaveGameDao
) : LocalRepository {

    override suspend fun getSaveGame(): SaveGame? {
        val saveGameRaw : SaveGameEntity = saveGameDao.getSaveGame() ?: return null

        val characterClass : PlayerClass = getPlayerClasses().first {it.classId == saveGameRaw.playerClassId} ?: return null

        val playerCharacter = PlayerCharacterImpl(
            characterClass = characterClass,
            characterHp = saveGameRaw.playerCurrentHp,
            characterMaxHp = saveGameRaw.playerMaxMp,
            characterMp = saveGameRaw.playerMaxMp,
            characterMaxMp = saveGameRaw.playerMaxMp,
            characterExp = saveGameRaw.playerExp
        )

        val saveSession = SaveSessionImpl(
            currentRoomIndex = saveGameRaw.currentRoomIndex,
            playerCharacter = playerCharacter
        )

        val userSettings = UserSettingsImpl(
            musicVolume = saveGameRaw.musicVolume,
            soundVolume = saveGameRaw.soundVolume,
            musicEnable = saveGameRaw.musicEnabled,
            soundEnable = saveGameRaw.soundEnabled,
            language = saveGameRaw.language
        )

        return SaveGameImpl(
            saveSession = saveSession,
            userSettings = userSettings,
            winCounter = saveGameRaw.winCounter
        )
    }

    override suspend fun saveSaveGame(saveGame: SaveGame) {

    }

    override fun getDungeonPreset(winCounter: Int): Dungeon {
        TODO("Not yet implemented")
    }

    private val playerClassList : List <PlayerClass> = listOf(
        PlayerClassImpl(classId = "cleric", displayName = "Cleric", unlockCountRequired = 0),
        PlayerClassImpl(classId = "druid", displayName = "Druid", unlockCountRequired = 1)
    )

    override fun getPlayerClasses(): List<PlayerClass> {
        return playerClassList
    }

    private val enemyTypeList : List <EnemyType> = listOf(
    EnemyTypeImpl(enemyTypeId = "humanoid", displayName = "enemy_type_humanoid"),
    EnemyTypeImpl(enemyTypeId = "beast", displayName = "enemy_type_beast"),
    EnemyTypeImpl(enemyTypeId = "undead", displayName = "enemy_type_undead"),
    EnemyTypeImpl(enemyTypeId = "dragon", displayName = "enemy_type_dragon"),
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