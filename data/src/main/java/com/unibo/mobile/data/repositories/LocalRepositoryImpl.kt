package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.gamedata.LocalGameData
import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.data.models.ability.AbilityImpl
import com.unibo.mobile.data.models.save.PlayerCharacterImpl
import com.unibo.mobile.data.models.save.SaveGameImpl
import com.unibo.mobile.data.models.save.SaveSessionImpl
import com.unibo.mobile.data.models.save.UserSettingsImpl
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.ability.AbilityType
import com.unibo.mobile.domain.model.entity.EnemyType
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.repositories.LocalRepository

class LocalRepositoryImpl(
    private val saveGameDao: SaveGameDao
) : LocalRepository {

    override suspend fun getSaveGame(): SaveGame? {
        val saveGameRaw: SaveGameEntity = saveGameDao.getSaveGame() ?: return null

        val userSettings = UserSettingsImpl(
            musicVolume = saveGameRaw.musicVolume,
            soundVolume = saveGameRaw.soundVolume,
            musicEnable = saveGameRaw.musicEnabled,
            soundEnable = saveGameRaw.soundEnabled,
            language = saveGameRaw.language
        )

        if (!saveGameRaw.hasActiveSession) {
            return SaveGameImpl(
                saveSession = null,
                userSettings = userSettings,
                winCounter = saveGameRaw.winCounter
            )
        }

        val characterClass: PlayerClass = getPlayerClasses()
            .firstOrNull { it.classId == saveGameRaw.playerClassId } ?: return null

        val placeholderAbilities: List<Ability> = saveGameRaw.playerAbilityIds
            .split(",")
            .filter { it.isNotBlank() }
            .map { id ->
                AbilityImpl(
                    abilityId = id,
                    displayName = id,
                    actionCost = LocalRepositoryConstants.PLACEHOLDER_ACTION_COST,
                    level = LocalRepositoryConstants.PLACEHOLDER_LEVEL,
                    abilityType = AbilityType.DAMAGE_SINGLE,
                    diceCount = LocalRepositoryConstants.PLACEHOLDER_DICE_COUNT,
                    diceType = LocalRepositoryConstants.PLACEHOLDER_DICE_TYPE,
                    flatExtraAmount = 0
                )
            }

        val playerCharacter = PlayerCharacterImpl(
            characterClass = characterClass,
            characterHp = saveGameRaw.playerCurrentHp,
            characterMaxHp = saveGameRaw.playerMaxHp,
            characterMp = saveGameRaw.playerCurrentMp,
            characterMaxMp = saveGameRaw.playerMaxMp,
            characterExp = saveGameRaw.playerExp,
            characterLevel = saveGameRaw.playerLevel,
            abilityList = placeholderAbilities,
        )

        val saveSession = SaveSessionImpl(
            currentRoomIndex = saveGameRaw.currentRoomIndex,
            playerCharacter = playerCharacter
        )

        return SaveGameImpl(
            saveSession = saveSession,
            userSettings = userSettings,
            winCounter = saveGameRaw.winCounter
        )
    }

    override suspend fun saveSaveGame(saveGame: SaveGame) {

        val playerAbilitiesIdList: String = saveGame.saveSession?.playerCharacter?.abilityList
            ?.joinToString(",") { it.abilityId } ?: ""

        val saveGameEntity = SaveGameEntity(
            id = LocalRepositoryConstants.SAVE_GAME_BASE_ID,
            winCounter = saveGame.winCounter,
            hasActiveSession = saveGame.saveSession != null,
            currentRoomIndex = saveGame.saveSession?.currentRoomIndex ?: 0,
            playerClassId = saveGame.saveSession?.playerCharacter?.characterClass?.classId ?: "",
            playerCurrentHp = saveGame.saveSession?.playerCharacter?.characterHp ?: 0,
            playerMaxHp = saveGame.saveSession?.playerCharacter?.characterMaxHp ?: 0,
            playerCurrentMp = saveGame.saveSession?.playerCharacter?.characterMp ?: 0,
            playerMaxMp = saveGame.saveSession?.playerCharacter?.characterMaxMp ?: 0,
            playerLevel = saveGame.saveSession?.playerCharacter?.characterLevel ?: 0,
            playerExp = saveGame.saveSession?.playerCharacter?.characterExp ?: 0,
            playerAbilityIds = playerAbilitiesIdList,
            musicEnabled = saveGame.userSettings.musicEnable,
            musicVolume = saveGame.userSettings.musicVolume,
            soundEnabled = saveGame.userSettings.soundEnable,
            soundVolume = saveGame.userSettings.musicVolume,
            language = saveGame.userSettings.language,
        )
        saveGameDao.insertOrUpdateSaveGame(
            entity = saveGameEntity
        )
    }

    override fun getPlayerClasses(): List<PlayerClass> {
        return LocalGameData.playerClasses
    }

    override fun getEnemyTypes(): List<EnemyType> {
        return LocalGameData.enemyTypes
    }
}