package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.SaveGame

interface DungeonWinUseCase {
    fun invoke(saveGame: SaveGame): SaveGame
}