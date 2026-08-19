package com.unibo.mobile.data.repositories

import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.repositories.SaveGameRepository

class SaveGameRepositoryImpl: SaveGameRepository {
    override suspend fun loadOrCreateGame(): SaveGame {
        TODO("Not yet implemented")
    }

    override suspend fun saveGame(saveGame: SaveGame) {
        TODO("Not yet implemented")
    }
}