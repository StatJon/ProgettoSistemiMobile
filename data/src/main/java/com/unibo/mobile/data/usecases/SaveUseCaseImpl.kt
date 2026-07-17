package com.unibo.mobile.data.usecases

import com.unibo.mobile.data.models.save.SaveGameImpl
import com.unibo.mobile.data.models.save.UserSettingsImpl
import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.repositories.LocalRepository
import com.unibo.mobile.domain.usecases.SaveUseCase

class SaveUseCaseImpl(
    private val localRepository: LocalRepository
) : SaveUseCase {
    override suspend fun loadGame(): SaveGame {
        return searchExistingSave() ?: createNewSave()
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

    private suspend fun searchExistingSave(): SaveGame? {
        return localRepository.getSaveGame()
    }

    private suspend fun createNewSave(): SaveGame {
        val newSave = SaveGameImpl(
            saveSession = null,
            userSettings = UserSettingsImpl(
                musicVolume = 0.5f,
                soundVolume = 0.5f,
                musicEnable = true,
                soundEnable = true,
                language = "en"
            ),
            winCounter = 0
        )
        saveGameProgress(newSave)
        return newSave
    }
}