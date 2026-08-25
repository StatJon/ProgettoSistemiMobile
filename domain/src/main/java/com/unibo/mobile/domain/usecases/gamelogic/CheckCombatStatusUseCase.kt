package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.CombatSnapshot
import com.unibo.mobile.domain.models.CombatStatus

interface CheckCombatStatusUseCase {
    fun invoke(combatSnapshot: CombatSnapshot): CombatStatus
}