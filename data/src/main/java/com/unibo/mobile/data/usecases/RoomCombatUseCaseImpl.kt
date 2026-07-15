package com.unibo.mobile.data.usecases

import com.unibo.mobile.data.gamedata.LocalGameData
import com.unibo.mobile.data.models.combat.CombatStateImpl
import com.unibo.mobile.data.models.combat.CombatLogEntryImpl
import com.unibo.mobile.data.models.combat.PostCombatRewardImpl
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.ability.AbilityType
import com.unibo.mobile.domain.model.ability.Dice
import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.combat.CombatOutcome
import com.unibo.mobile.domain.model.combat.CombatState
import com.unibo.mobile.domain.model.combat.PostCombatReward
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.strategy.CommonActionStrategy
import com.unibo.mobile.domain.model.strategy.DiceThrowStrategy
import com.unibo.mobile.domain.model.strategy.NpcStrategy
import com.unibo.mobile.domain.model.strategy.ResourceCost
import com.unibo.mobile.domain.model.strategy.TurnCalculator
import com.unibo.mobile.domain.usecases.RoomCombatUseCase

class RoomCombatUseCaseImpl(
    private val turnCalculator: TurnCalculator,
    private val npcStrategy: NpcStrategy,
    private val diceThrowStrategy: DiceThrowStrategy,
    private val commonActionStrategy: CommonActionStrategy,
    private val resourceCost: ResourceCost
) : RoomCombatUseCase {

    override fun initCombat(allies: List<Ally>, enemies: List<Enemy>): CombatState {
        val allEntities: List<CombatEntity> = allies + enemies
        val orderedTurns = turnCalculator.orderTurns(allEntities)
        return CombatStateImpl(
            turnOrder = orderedTurns,
            turnIndex = 0,
            log = emptyList(),
            status = CombatOutcome.ONGOING
        )
    }

    override fun resolveAttackPlayer(
        actor: CombatEntity,
        ability: Ability,
        target: CombatEntity,
        allEntities: List<CombatEntity>
    ): Action {
        return resolveSingleAction(actor, ability, target)
    }

    override fun resolveAttackNpc(
        actor: CombatEntity,
        allEntities: List<CombatEntity>
    ): List<Action> {
        val ability = npcStrategy.selectAbility(actor.abilities)
        val validTargets = commonActionStrategy.findTargets(actor, ability, allEntities)

        val chosenTargets = when (ability.abilityType) {
            AbilityType.DAMAGE_AOE, AbilityType.HEAL_AOE -> validTargets
            else -> npcStrategy.selectTarget(validTargets)
        }

        return chosenTargets.map { target -> resolveSingleAction(actor, ability, target) }
    }

    // Helper function for resolveAttackPlayer/Npc
    private fun resolveSingleAction(
        actor: CombatEntity,
        ability: Ability,
        target: CombatEntity
    ): Action {
        val attackRoll = diceThrowStrategy.throwDice(1, Dice.D20, 0)
        val isHit = commonActionStrategy.resolveAttackRoll(attackRoll, target.armorClass)
        val amount = if (isHit) {
            diceThrowStrategy.throwDice(
                ability.diceCount,
                ability.diceType,
                ability.flatExtraAmount
            )
        } else {
            0
        }
        return commonActionStrategy.buildAction(actor, target, ability, isHit, amount, attackRoll)
    }

    override fun executeAction(currentState: CombatState, actionToExecute: Action): CombatState {
        val signedAmount = when (actionToExecute.ability.abilityType) {
            AbilityType.DAMAGE_SINGLE, AbilityType.DAMAGE_AOE -> -actionToExecute.amount
            AbilityType.HEAL_SINGLE, AbilityType.HEAL_AOE -> actionToExecute.amount
        }

        val actorAfterCost =
            resourceCost.consumeResource(actionToExecute.actor, actionToExecute.ability)
        val updatedTarget = actionToExecute.target.changeHp(signedAmount)

        val updatedTurnOrder = currentState.turnOrder.map { entity ->
            when (entity.entityId) {
                actorAfterCost.entityId -> actorAfterCost
                updatedTarget.entityId -> updatedTarget
                else -> entity
            }
        }

        val mpCost = -actionToExecute.ability.level

        val logEntry = CombatLogEntryImpl(
            actorName = actionToExecute.actor.displayName,
            actionName = actionToExecute.ability.displayName,
            targetName = actionToExecute.target.displayName,
            wasHit = actionToExecute.isHit,
            hpChanged = signedAmount,
            attackRoll = actionToExecute.attackRoll,
            defenseRoll = actionToExecute.target.armorClass,
            mpChanged = mpCost
        )

        val allEnemiesDead = updatedTurnOrder.filterIsInstance<Enemy>().all { !it.isAlive() }
        val allAlliesDead = updatedTurnOrder.filterIsInstance<Ally>().all { !it.isAlive() }

        val outcome = when {
            allEnemiesDead -> CombatOutcome.VICTORY
            allAlliesDead -> CombatOutcome.DEFEAT
            else -> CombatOutcome.ONGOING
        }

        return CombatStateImpl(
            turnOrder = updatedTurnOrder,
            turnIndex = currentState.turnIndex,
            log = currentState.log + logEntry,
            status = outcome
        )
    }

    override fun advanceTurn(currentState: CombatState): CombatState {
        val nextIndex = (currentState.turnIndex + 1) % currentState.turnOrder.size
        return CombatStateImpl(
            turnOrder = currentState.turnOrder,
            turnIndex = nextIndex,
            log = currentState.log,
            status = currentState.status
        )
    }

    override fun calculatePostCombatReward(enemyList: List<Enemy>): PostCombatReward {
        val hpRestore = enemyList.size * LocalGameData.HP_RESTORE_BASE
        val mpRestore = enemyList.size * LocalGameData.MP_RESTORE_BASE
        return PostCombatRewardImpl(hpRestore = hpRestore, mpRestore = mpRestore)
    }

    override fun updatePlayerCharacter(
        playerCharacter: PlayerCharacter,
        allies: List<Ally>,
        reward: PostCombatReward?
    ): PlayerCharacter {
        val ally = allies.first()

        val hpDelta = (ally.currentHp - playerCharacter.characterHp)
        val mpDelta = (ally.currentMp - playerCharacter.characterMp)

        var updated = playerCharacter.changeHp(hpDelta).changeMp(mpDelta)

        if (reward != null) {
            updated = updated.changeHp(reward.hpRestore).changeMp(reward.mpRestore)
        }

        return updated
    }
}