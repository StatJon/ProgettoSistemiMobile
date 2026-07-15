package com.unibo.mobile.data.usecases

import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.repositories.LocalRepository
import com.unibo.mobile.domain.usecases.SaveUseCase

class SaveUseCaseImpl(
    private val localRepository: LocalRepository
) : SaveUseCase{
    override suspend fun loadGame(): SaveGame? {
        return localRepository.getSaveGame()
    }

    override suspend fun saveGameProgress(saveGame: SaveGame): SaveGame {
        localRepository.saveSaveGame(saveGame)
        return saveGame
    }

    override suspend fun addWinCounter(saveGame: SaveGame): SaveGame {
        val updatedSaveGame = saveGame.incrementWinCounter()
        localRepository.saveSaveGame(updatedSaveGame)
        return updatedSaveGame
    }
}