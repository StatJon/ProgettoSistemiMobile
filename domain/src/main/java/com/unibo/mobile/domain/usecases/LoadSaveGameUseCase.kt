package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.repositories.SaveGameRepository

interface LoadSaveGameUseCase {
    suspend fun invoke(): SaveGame
}

class LoadSaveGameUseCaseImpl(
    private val saveGameRepository: SaveGameRepository
) : LoadSaveGameUseCase {

    override suspend fun invoke(): SaveGame {
        return saveGameRepository.loadOrCreateGame()
    }
}