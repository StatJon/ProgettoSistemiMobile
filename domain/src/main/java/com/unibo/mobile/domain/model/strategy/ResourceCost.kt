package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.CombatEntity

interface ResourceCost {
    fun canAfford(pool: Int, cost: Int): Boolean
    fun consumeResource(entity: CombatEntity, ability: Ability): CombatEntity
}
