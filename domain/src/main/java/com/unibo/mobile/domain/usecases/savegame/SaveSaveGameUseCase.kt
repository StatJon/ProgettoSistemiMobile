package com.unibo.mobile.domain.usecases.savegame

import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.repositories.SaveGameRepository

interface SaveSaveGameUseCase {
    suspend fun invoke(saveGame: SaveGame)
}

class SaveSaveGameUseCaseImpl(
    private val saveGameRepository: SaveGameRepository
) : SaveSaveGameUseCase {
    override suspend fun invoke(saveGame: SaveGame) {
        saveGameRepository.saveSaveGame(saveGame)
    }
}