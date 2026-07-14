package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.CombatEntity

interface NpcStrategy {
    fun selectAbility(abilityList: List<Ability>): Ability
    fun selectTarget(entityList: List<CombatEntity>): List<CombatEntity>
}