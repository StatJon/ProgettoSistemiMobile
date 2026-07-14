package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.data.models.entity.AllyImpl
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.strategy.ResourceCost

class ResourceCostDefault : ResourceCost {
    override fun canAfford(pool: Int, cost: Int): Boolean {
        return (pool - cost) >= 0
    }

    override fun consumeResource(
        entity: CombatEntity,
        ability: Ability
    ): CombatEntity {

        val actionCost = ability.actionCost
        val manaCost = ability.level

        return when (entity) {
            is AllyImpl -> {
                entity
                    .changeAp(-actionCost)
                    .changeMp(-manaCost)
            }

            else -> {
                entity
                    .changeAp(-actionCost)
            }
        }
    }
}
