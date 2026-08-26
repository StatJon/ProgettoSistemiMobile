package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.Dungeon
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.models.SaveSession
import com.unibo.mobile.domain.repositories.AbilityRepository
import com.unibo.mobile.domain.repositories.PlayerClassRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

class SaveGameRepositoryImpl(
    private val saveGameDao: SaveGameDao,
    private val playerClassRepository: PlayerClassRepository,
    private val abilityRepository: AbilityRepository
) : SaveGameRepository {

    override suspend fun loadOrCreateGame(): SaveGame {
        val rawSaveData: SaveGameEntity? = saveGameDao.loadSaveGame()
        return if (rawSaveData != null) {
            mapRawToSaveGame(
                rawSaveData,
                playerClassRepository = playerClassRepository
            )
        } else {
            createNewSave()
        }
    }

    override suspend fun saveSaveGame(saveGame: SaveGame) {
        val saveGameEntity = mapSaveGameToRaw(saveGame)
        saveGameDao.saveSaveGame(saveGameEntity)
    }

    // --- Private Helpers
    private fun createNewSave(): SaveGame {
        return SaveGame(
            winCounter = 0,
            saveSession = null
        )
    }

    // TODO Nota: Valutare (anche di tempo) se sostituire !! con 2 DAO/Tabelle
    private suspend fun mapRawToSaveGame(
        rawSaveData: SaveGameEntity,
        playerClassRepository: PlayerClassRepository
    ): SaveGame {
        return if (rawSaveData.dungeonIndex != null && rawSaveData.dungeonLength != null) {
            SaveGame(
                winCounter = rawSaveData.winCounter,
                saveSession = SaveSession(
                    dungeon = Dungeon(
                        dungeonIndex = rawSaveData.dungeonIndex,
                        dungeonLength = rawSaveData.dungeonLength,
                    ),
                    playerCharacter = CharacterPlayer(
                        playerClass = playerClassRepository.getPlayerClassByName(rawSaveData.playerClassName!!)
                            ?: error("PlayerClass $rawSaveData.playerClassName missing"),
                        currentManaPoints = rawSaveData.currentManaPoints!!,
                        maxManaPoints = rawSaveData.maxManaPoints!!,
                        characterData = CharacterData(
                            name = rawSaveData.name!!,
                            maxHealthPoints = rawSaveData.maxHealthPoints!!,
                            currentHealthPoints = rawSaveData.currentHealthPoints!!,
                            armorClass = rawSaveData.armorClass!!,
                            abilityList = abilityRepository.getAbilityFromIndexList(
                                rawSaveData.abilityNames?.split(
                                    ","
                                )!!
                            )
                        ),

                        )
                )
            )
        } else {
            SaveGame(
                winCounter = rawSaveData.winCounter,
                saveSession = null
            )
        }
    }

    private fun mapSaveGameToRaw(saveGame: SaveGame): SaveGameEntity {
        val saveSession = saveGame.saveSession
        val playerCharacter = saveGame.saveSession?.playerCharacter
        val character = saveGame.saveSession?.playerCharacter?.characterData
        return SaveGameEntity(
            winCounter = saveGame.winCounter,
            dungeonIndex = saveSession?.dungeon?.dungeonIndex,
            dungeonLength = saveSession?.dungeon?.dungeonLength,
            playerClassName = playerCharacter?.playerClass?.className,
            currentManaPoints = playerCharacter?.currentManaPoints,
            maxManaPoints = playerCharacter?.maxManaPoints,
            name = playerCharacter?.characterData?.name,
            maxHealthPoints = character?.maxHealthPoints,
            currentHealthPoints = character?.currentHealthPoints,
            armorClass = character?.armorClass,
            abilityNames = character?.abilityList?.map { it.name }?.joinToString { (",") }
        )
    }
}