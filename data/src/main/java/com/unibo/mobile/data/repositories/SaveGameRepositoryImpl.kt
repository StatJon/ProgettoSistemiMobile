package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.domain.models.Character
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.models.SaveSession
import com.unibo.mobile.domain.repositories.PlayerClassRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

class SaveGameRepositoryImpl(
    private val saveGameDao: SaveGameDao,
    private val playerClassRepository: PlayerClassRepository
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
            winCounter = 10, //TODO RIMETTERE A 0
            saveSession = null
        )
    }

    // Nota: Valutare (anche di tempo) se sostituire !! con 2 DAO/Tabelle
    private suspend fun mapRawToSaveGame(
        rawSaveData: SaveGameEntity,
        playerClassRepository: PlayerClassRepository
    ): SaveGame {
        return if (rawSaveData.dungeonIndex != null) {
            SaveGame(
                winCounter = rawSaveData.winCounter,
                saveSession = SaveSession(
                    dungeonIndex = rawSaveData.dungeonIndex,
                    playerCharacter = CharacterPlayer(
                        playerClass = playerClassRepository.getPlayerClassByName(rawSaveData.playerClassName!!)
                            ?: error("PlayerClass $rawSaveData.playerClassName missing"),
                        currentManaPoints = rawSaveData.currentManaPoints!!,
                        maxManaPoints = rawSaveData.maxManaPoints!!,
                        character = Character(
                            name = rawSaveData.name!!,
                            maxHealthPoints = rawSaveData.maxHealthPoints!!,
                            currentHealthPoints = rawSaveData.currentHealthPoints!!,
                            armorClass = rawSaveData.armorClass!!
                        )
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
        return SaveGameEntity(
            winCounter = saveGame.winCounter,
            dungeonIndex = saveGame.saveSession?.dungeonIndex,
            playerClassName = saveGame.saveSession?.playerCharacter?.playerClass?.className,
            currentManaPoints = saveGame.saveSession?.playerCharacter?.currentManaPoints,
            maxManaPoints = saveGame.saveSession?.playerCharacter?.maxManaPoints,
            name = saveGame.saveSession?.playerCharacter?.character?.name,
            maxHealthPoints = saveGame.saveSession?.playerCharacter?.character?.maxHealthPoints,
            currentHealthPoints = saveGame.saveSession?.playerCharacter?.character?.currentHealthPoints,
            armorClass = saveGame.saveSession?.playerCharacter?.character?.armorClass
        )
    }
}