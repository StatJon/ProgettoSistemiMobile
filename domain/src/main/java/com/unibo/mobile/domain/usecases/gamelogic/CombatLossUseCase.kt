package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.SaveGame

interface CombatLossUseCase {
    fun invoke(saveGame: SaveGame): SaveGame
}