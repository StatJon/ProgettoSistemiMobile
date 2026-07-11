package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.entity.CombatEntity

interface TurnCalculator {
    fun orderTurns(entities: List<CombatEntity>): List<CombatEntity>
}