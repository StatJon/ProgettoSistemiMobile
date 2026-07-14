package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.strategy.TurnCalculator

class TurnCalculatorSingleAllyDefault : TurnCalculator {
    override fun orderTurns(entities: List<CombatEntity>): List<CombatEntity> {
        val ally = entities.filterIsInstance<Ally>().first()
        val enemyEntities = entities.filterIsInstance<Enemy>()
        return enemyEntities.flatMap { enemy -> listOf(ally, enemy) }
    }
}