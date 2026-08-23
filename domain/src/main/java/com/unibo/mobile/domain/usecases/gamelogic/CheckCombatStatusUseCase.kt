package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.CombatStatus

interface CheckCombatStatusUseCase {
    fun invoke(player: CharacterPlayer, enemy: CharacterEnemy): CombatStatus
}