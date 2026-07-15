package com.unibo.mobile.data.models.combat

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.entity.CombatEntity

data class ActionImpl(
    override val actor: CombatEntity,
    override val target: CombatEntity,
    override val ability: Ability,
    override val isHit: Boolean,
    override val amount: Int,
    override val attackRoll: Int
) : Action {
}