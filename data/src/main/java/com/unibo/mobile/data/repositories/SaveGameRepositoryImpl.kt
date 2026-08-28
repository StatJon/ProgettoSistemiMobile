package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.Dungeon
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.models.SaveSession
import com.unibo.mobile.domain.repositories.AbilityRepository
import com.unibo.mobile.domain.repositories.GamedataRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

class SaveGameRepositoryImpl(
    private val saveGameDao: SaveGameDao,
    private val gamedataRepository: GamedataRepository,
    private val abilityRepository: AbilityRepository
) : SaveGameRepository {

    override suspend fun loadOrCreateGame(): SaveGame {
        val rawSaveData: SaveGameEntity? = saveGameDao.loadSaveGame()
        return if (rawSaveData != null) {
            mapRawToSaveGame(
                rawSaveData, gamedataRepository = gamedataRepository
            )
        } else {
            createNewSave()
        }
    }

    override suspend fun saveSaveGame(saveGame: SaveGame) {
        val saveGameEntity = mapSaveGameToRaw(saveGame)
        saveGameDao.saveSaveGame(saveGameEntity)
    }

    override suspend fun createNewSaveSessionAndSave(
        saveGame: SaveGame,
        playerClassName: String
    ): SaveGame {
        val newSaveSession = createNewSaveSession(saveGame, playerClassName)
        val newSaveGame = SaveGame(
            winCounter = saveGame.winCounter,
            saveSession = newSaveSession
        )
        saveSaveGame(newSaveGame)
        return newSaveGame
    }

    // --- Private Helpers
    private fun createNewSave(): SaveGame {
        return SaveGame(
            winCounter = 0, saveSession = null
        )
    }

    // From DB, Load
    private suspend fun mapRawToSaveGame(
        rawSaveData: SaveGameEntity, gamedataRepository: GamedataRepository
    ): SaveGame {
        return if (rawSaveData.dungeonIndex != null && rawSaveData.dungeonLength != null) {
            val playerClass = gamedataRepository.getPlayerClassByName(rawSaveData.playerClassName!!)
                ?: error("PlayerClass $rawSaveData.playerClassName missing")
            SaveGame(
                winCounter = rawSaveData.winCounter, saveSession = SaveSession(
                    dungeon = Dungeon(
                        dungeonIndex = rawSaveData.dungeonIndex,
                        dungeonLength = rawSaveData.dungeonLength,
                    ), characterPlayer = CharacterPlayer(
                        playerClass = playerClass,
                        currentManaPoints = rawSaveData.currentManaPoints!!,
                        maxManaPoints = rawSaveData.maxManaPoints!!,
                        level = rawSaveData.level!!,
                        characterData = CharacterData(
                            name = rawSaveData.name!!,
                            maxHealthPoints = rawSaveData.maxHealthPoints!!,
                            currentHealthPoints = rawSaveData.currentHealthPoints!!,
                            armorClass = rawSaveData.armorClass!!,
                            abilityList = (playerClass.baseAbilityList +
                                    abilityRepository.getAbilityFromIndexList(
                                        rawSaveData.abilityIndexList?.split(
                                            ","
                                        ) ?: emptyList()
                                    ))
                                .distinctBy { it.name }
                        ),
                    )
                )
            )
        } else {
            SaveGame(
                winCounter = rawSaveData.winCounter, saveSession = null
            )
        }
    }

    // Towards DB, Save
    private fun mapSaveGameToRaw(saveGame: SaveGame): SaveGameEntity {
        val saveSession = saveGame.saveSession
        val playerCharacter = saveGame.saveSession?.characterPlayer
        val character = saveGame.saveSession?.characterPlayer?.characterData
        return SaveGameEntity(
            winCounter = saveGame.winCounter,
            dungeonIndex = saveSession?.dungeon?.dungeonIndex,
            dungeonLength = saveSession?.dungeon?.dungeonLength,
            playerClassName = playerCharacter?.playerClass?.className,
            currentManaPoints = playerCharacter?.currentManaPoints,
            maxManaPoints = playerCharacter?.maxManaPoints,
            name = playerCharacter?.characterData?.name,
            level = playerCharacter?.level,
            maxHealthPoints = character?.maxHealthPoints,
            currentHealthPoints = character?.currentHealthPoints,
            armorClass = character?.armorClass,
            abilityIndexList = character?.abilityList?.joinToString(",") { it.name })
    }

    private fun createNewSaveSession(saveGame: SaveGame, playerClassName: String): SaveSession {
        val playerClass = gamedataRepository.getPlayerClassByName(playerClassName)
            ?: error("Error: PlayerClass not found")
        val newSaveSession = SaveSession(
            dungeon = Dungeon(
                dungeonIndex = 0,
                dungeonLength = gamedataRepository.getDungeonBaseLength() + saveGame.winCounter
            ), characterPlayer = CharacterPlayer(
                playerClass = playerClass,
                currentManaPoints = playerClass.baseManaPoints,
                maxManaPoints = playerClass.baseManaPoints,
                level = 1,
                characterData = CharacterData(
                    name = playerClass.name,
                    maxHealthPoints = playerClass.baseHealthPoints,
                    currentHealthPoints = playerClass.baseHealthPoints,
                    armorClass = playerClass.baseArmorClass,
                    abilityList = playerClass.baseAbilityList
                )
            )
        )
        return newSaveSession
    }
}