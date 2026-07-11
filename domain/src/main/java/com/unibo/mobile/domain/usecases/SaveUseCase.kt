package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.save.SaveGame

interface SaveUseCase {
    fun loadGame(): SaveGame?
    fun saveGameProgress(saveGame: SaveGame): SaveGame
    fun addWinCounter(saveGame: SaveGame): SaveGame
}
