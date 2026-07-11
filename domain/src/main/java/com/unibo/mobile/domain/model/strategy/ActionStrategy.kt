package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.entity.CombatEntity

interface ActionStrategy {

    fun selectAction(availableAbilities: List<Ability>): Ability

    fun selectTarget(
        ability: Ability,
        availableTargets: List<CombatEntity>
    ): List<CombatEntity>

    fun resolveAttackHit(
        actor: CombatEntity,
        target: CombatEntity
    ): Boolean

    fun calculateAmountToChange(
        actor: CombatEntity,
        ability: Ability
    ): Int

    fun buildAction(
        actor: CombatEntity,
        availableAbilities: List<Ability>,
        availableTargets: List<CombatEntity>
    ): List<Action>
}
