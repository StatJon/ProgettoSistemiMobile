package com.unibo.mobile.domain.usecases.savegame

import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.repositories.SaveGameRepository

interface NewSaveSessionUseCase {
    suspend fun invoke(saveGame: SaveGame, playerClass: PlayerClass): SaveGame
}

class NewSaveSessionUseCaseImpl(
    private val saveGameRepository: SaveGameRepository
) : NewSaveSessionUseCase {
    override suspend fun invoke(
        saveGame: SaveGame,
        playerClass: PlayerClass
    ): SaveGame {
        val playerClassName = playerClass.className
        return saveGameRepository.createNewSaveSessionAndSave(saveGame, playerClassName)
    }
}