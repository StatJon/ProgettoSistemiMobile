package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.CombatSnapshot
import com.unibo.mobile.domain.models.CombatStatus

interface CheckCombatStatusUseCase {
    fun invoke(combatSnapshot: CombatSnapshot): CombatStatus
}

class CheckCombatStatusUseCaseImpl : CheckCombatStatusUseCase {
    override fun invoke(combatSnapshot: CombatSnapshot): CombatStatus {
        return when {
            combatSnapshot.player.characterData.currentHealthPoints <= 0 -> CombatStatus.DEFEAT
            combatSnapshot.enemy.characterData.currentHealthPoints <= 0 -> CombatStatus.VICTORY
            combatSnapshot.combatStatus == CombatStatus.PLAYER_TURN -> CombatStatus.ENEMY_TURN
            combatSnapshot.combatStatus == CombatStatus.ENEMY_TURN -> CombatStatus.PLAYER_TURN
            else -> CombatStatus.PLAYER_TURN //To silence compiler
        }
    }

}