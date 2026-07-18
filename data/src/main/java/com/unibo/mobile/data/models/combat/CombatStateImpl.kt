package com.unibo.mobile.data.models.combat

import com.unibo.mobile.domain.model.combat.CombatLogEntry
import com.unibo.mobile.domain.model.combat.CombatOutcome
import com.unibo.mobile.domain.model.combat.CombatState
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy


data class CombatStateImpl(
    override val turnOrder: List<CombatEntity>,
    override val turnIndex: Int,
    override val log: List<CombatLogEntry>,
    override val status: CombatOutcome
) : CombatState {
    override fun getActiveEntity(): CombatEntity {
        return turnOrder[turnIndex]
    }

    override fun getAllEnemies(): List<Enemy> {
        return turnOrder.filterIsInstance<Enemy>().distinctBy { it.entityId }
    }

    override fun getAllAllies(): List<Ally> {
        return turnOrder.filterIsInstance<Ally>().distinctBy { it.entityId }
    }
}