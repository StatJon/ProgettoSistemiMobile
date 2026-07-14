package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.data.models.combat.ActionImpl
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.ability.AbilityType
import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.strategy.CommonActionStrategy

class CommonActionStrategyDefault : CommonActionStrategy {
    override fun findTargets(
        actor: CombatEntity,
        ability: Ability,
        entityList: List<CombatEntity>
    ): List<CombatEntity> {

        val allyList: List<CombatEntity> = entityList.filterIsInstance<Ally>()
        val enemyList: List<CombatEntity> = entityList.filterIsInstance<Enemy>()
        val abilityType = ability.abilityType


        return when (actor) {
            is Ally -> {
                when (abilityType) {
                    AbilityType.DAMAGE_SINGLE, AbilityType.DAMAGE_AOE -> enemyList
                    AbilityType.HEAL_SINGLE, AbilityType.HEAL_AOE -> allyList
                }
            }

            is Enemy -> {
                when (ability.abilityType) {
                    AbilityType.DAMAGE_SINGLE, AbilityType.DAMAGE_AOE -> allyList
                    AbilityType.HEAL_SINGLE, AbilityType.HEAL_AOE -> enemyList
                }
            }

            else -> emptyList()
        }
    }

    override fun resolveAttackRoll(
        attackDiceValue: Int,
        armorClass: Int
    ): Boolean {
        return (attackDiceValue >= armorClass)
    }

    override fun buildAction(
        actor: CombatEntity,
        target: CombatEntity,
        ability: Ability,
        isHit: Boolean,
        amount: Int
    ): Action {
        return ActionImpl(
            actor = actor,
            target = target,
            ability = ability,
            isHit = isHit,
            amount = amount
        )
    }
}