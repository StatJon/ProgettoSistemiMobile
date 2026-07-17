package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.save.SaveGame

interface SaveUseCase {
    suspend fun loadGame(): SaveGame
    suspend fun saveGameProgress(saveGame: SaveGame): SaveGame
    suspend fun addWinCounter(saveGame: SaveGame): SaveGame
}
