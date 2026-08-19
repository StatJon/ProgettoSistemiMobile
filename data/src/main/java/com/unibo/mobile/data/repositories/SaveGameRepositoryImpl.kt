package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.domain.models.Character
import com.unibo.mobile.domain.models.PlayerCharacter
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.models.SaveSession
import com.unibo.mobile.domain.repositories.SaveGameRepository

class SaveGameRepositoryImpl(
    private val saveGameDao: SaveGameDao
) : SaveGameRepository {
    override suspend fun loadOrCreateGame(): SaveGame {
        val rawSaveData: SaveGameEntity? = saveGameDao.loadSaveGame()
        return if (rawSaveData != null) {
            mapRawToSaveGame(rawSaveData)
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

    // Nota: Valutare (anche di tempo) se sostituire !! con 2 DAO/Tabelle
    private fun mapRawToSaveGame(rawSaveData: SaveGameEntity): SaveGame {
        return if (rawSaveData.dungeonIndex != null) {
            SaveGame(
                winCounter = rawSaveData.winCounter,
                saveSession = SaveSession(
                    dungeonIndex = rawSaveData.dungeonIndex,
                    playerCharacter = PlayerCharacter(
                        playerClass = rawSaveData.playerClass!!,
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
            playerClass = saveGame.saveSession?.playerCharacter?.playerClass,
            currentManaPoints = saveGame.saveSession?.playerCharacter?.currentManaPoints,
            maxManaPoints = saveGame.saveSession?.playerCharacter?.maxManaPoints,
            name = saveGame.saveSession?.playerCharacter?.character?.name,
            maxHealthPoints = saveGame.saveSession?.playerCharacter?.character?.maxHealthPoints,
            currentHealthPoints = saveGame.saveSession?.playerCharacter?.character?.currentHealthPoints,
            armorClass = saveGame.saveSession?.playerCharacter?.character?.armorClass
        )
    }
}