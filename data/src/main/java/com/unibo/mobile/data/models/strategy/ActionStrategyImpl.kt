package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.strategy.ActionStrategy

class ActionStrategyImpl : ActionStrategy {
    override fun selectAction(availableAbilities: List<Ability>): Ability {
        TODO("Not yet implemented")
    }

    override fun selectTarget(
        ability: Ability,
        availableTargets: List<CombatEntity>
    ): List<CombatEntity> {
        TODO("Not yet implemented")
    }

    override fun resolveAttackHit(
        actor: CombatEntity,
        target: CombatEntity
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun calculateAmountToChange(
        actor: CombatEntity,
        ability: Ability
    ): Int {
        TODO("Not yet implemented")
    }

    override fun buildAction(
        actor: CombatEntity,
        availableAbilities: List<Ability>,
        availableTargets: List<CombatEntity>
    ): List<Action> {
        TODO("Not yet implemented")
    }
}