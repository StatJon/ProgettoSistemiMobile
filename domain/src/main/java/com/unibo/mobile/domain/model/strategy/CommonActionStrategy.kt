package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.entity.CombatEntity

interface CommonActionStrategy {
    fun findTargets(
        actor: CombatEntity,
        ability: Ability,
        entityList: List<CombatEntity>
    ): List<CombatEntity>

    fun resolveAttackRoll(
        attackDiceValue: Int,
        armorClass: Int
    ): Boolean

    fun buildAction(
        actor: CombatEntity,
        target: CombatEntity,
        ability: Ability,
        isHit: Boolean,
        amount: Int
    ): Action
}
