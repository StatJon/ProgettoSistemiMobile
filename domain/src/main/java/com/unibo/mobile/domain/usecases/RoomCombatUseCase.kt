package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.combat.CombatLogEntry
import com.unibo.mobile.domain.model.combat.CombatState
import com.unibo.mobile.domain.model.combat.PostCombatReward
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy

interface RoomCombatUseCase {
    fun initCombat(
        allies: List<Ally>,
        enemies: List<Enemy>
    ): CombatState

    fun executeAction(
        currentState: CombatState,
        actionToExecute: Action
    ): CombatState

    fun writeNewLog(action: Action): CombatLogEntry

    fun advanceTurn(currentState: CombatState): CombatState

    fun isDefeated(entity: CombatEntity): Boolean

    fun isCombatOver(currentState: CombatState): Boolean

    fun calculcatePostCombatReward(enemyList: List<Enemy>): PostCombatReward

    fun assignPostCombatReward(
        ally: Ally,
        reward: PostCombatReward
    ): Ally

}
