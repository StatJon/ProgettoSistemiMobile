package com.unibo.mobile.domain.model.combat

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.CombatEntity

interface Action {
    val actor: CombatEntity
    val target: List<CombatEntity>
    val ability: Ability
}