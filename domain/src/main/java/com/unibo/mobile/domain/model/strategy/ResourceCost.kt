package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.CombatEntity

interface ResourceCost {
    fun canAfford(actor: CombatEntity, ability: Ability): Boolean
    fun consumeResource(actor: CombatEntity, ability: Ability): CombatEntity
}
