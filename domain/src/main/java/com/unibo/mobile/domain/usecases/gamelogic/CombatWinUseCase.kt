package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.SaveGame

interface CombatWinUseCase {
    fun invoke(dungeonIndex: Int): SaveGame
}