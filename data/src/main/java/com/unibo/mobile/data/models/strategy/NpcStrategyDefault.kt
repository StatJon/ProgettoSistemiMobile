package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.strategy.NpcStrategy

class NpcStrategyDefault : NpcStrategy {
    override fun selectAbility(abilityList: List<Ability>): Ability {
        return abilityList.random()
    }

    override fun selectTarget(entityList: List<CombatEntity>): List<CombatEntity> {
        return listOf(entityList.random())
    }
}
