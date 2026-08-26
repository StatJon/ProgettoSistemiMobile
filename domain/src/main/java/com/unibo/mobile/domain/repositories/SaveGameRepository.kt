package com.unibo.mobile.domain.repositories

import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.models.SaveGame

interface SaveGameRepository {
    suspend fun loadOrCreateGame(): SaveGame
    suspend fun saveSaveGame(saveGame: SaveGame)
    suspend fun createNewSaveSessionAndSave(saveGame: SaveGame, playerClassName: String): SaveGame
}