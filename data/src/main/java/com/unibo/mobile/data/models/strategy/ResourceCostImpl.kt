package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.strategy.ResourceCost

class ResourceCostImpl : ResourceCost {
    override fun canAfford(
        actor: CombatEntity,
        ability: Ability
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun consumeResource(
        actor: CombatEntity,
        ability: Ability
    ): CombatEntity {
        TODO("Not yet implemented")
    }
}