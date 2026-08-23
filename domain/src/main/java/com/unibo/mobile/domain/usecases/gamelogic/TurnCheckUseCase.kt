package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.CombatSnapshot

interface TurnCheckUseCase {
    fun invoke(combatSnapshot: CombatSnapshot): CombatSnapshot
}