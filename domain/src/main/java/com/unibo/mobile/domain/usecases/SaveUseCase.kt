package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.save.SaveGame

interface SaveUseCase {
    suspend fun loadGame(): SaveGame
    suspend fun newSession(playerCharacter: PlayerCharacter): SaveGame
    suspend fun saveGameProgress(saveGame: SaveGame): SaveGame
    suspend fun addWinCounter(saveGame: SaveGame): SaveGame
}
