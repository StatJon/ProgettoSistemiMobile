package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.GamePhase

interface DetermineGamePhaseUseCase {
    fun invoke(dungeonIndex: Int, dungeonLength: Int): GamePhase
}

class DetermineGamePhaseUseCaseImpl : DetermineGamePhaseUseCase {
    override fun invoke(
        dungeonIndex: Int,
        dungeonLength: Int
    ): GamePhase {
        return when {
            dungeonIndex < dungeonLength -> GamePhase.COMBAT
            else -> GamePhase.DUNGEON_WON
        }
    }
}