package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.GamePhase

interface DetermineGamePhaseUseCase {
    fun invoke(dungeonIndex: Int, dungeonLength: Int): GamePhase
}