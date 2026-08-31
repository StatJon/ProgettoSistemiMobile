package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityHeal
import com.unibo.mobile.domain.models.CombatSnapshot

interface ExecuteTurnUseCase {
    fun invoke(
        combatSnapshot: CombatSnapshot,
        ability: Ability,
        isPlayerTurn: Boolean
    ): CombatSnapshot
}

class ExecuteTurnUseCaseImpl(
    private val calculateAbilityResultUseCase: CalculateAbilityResultUseCase,
    private val applyAbilityResultUseCase: ApplyAbilityResultUseCase,
    private val applyPlayerAbilityCostUseCase: ApplyPlayerAbilityCostUseCase

) : ExecuteTurnUseCase {
    override fun invoke(
        combatSnapshot: CombatSnapshot,
        ability: Ability,
        isPlayerTurn: Boolean
    ): CombatSnapshot {

        val caster = if (isPlayerTurn) combatSnapshot.player else combatSnapshot.enemy
        val target = if (ability is AbilityHeal) caster
        else if (isPlayerTurn) combatSnapshot.enemy
        else combatSnapshot.player

        val abilityResult = calculateAbilityResultUseCase.invoke(target, ability)
        val updatedTargetData = applyAbilityResultUseCase.invoke(target, abilityResult)

        val updatedSnapshot = if (isPlayerTurn) {
            val updatedPlayer = applyPlayerAbilityCostUseCase.invoke(
                combatSnapshot.player,
                abilityResult.ability
            )
            if (target == combatSnapshot.player) {
                combatSnapshot.copy(
                    player = updatedPlayer.copy(characterData = updatedTargetData),
                    enemy = combatSnapshot.enemy
                )
            } else {
                combatSnapshot.copy(
                    player = updatedPlayer,
                    enemy = combatSnapshot.enemy.copy(characterData = updatedTargetData)
                )
            }
        } else {
            if (target == combatSnapshot.enemy) {
                combatSnapshot.copy(
                    player = combatSnapshot.player,
                    enemy = combatSnapshot.enemy.copy(characterData = updatedTargetData)
                )
            } else {
                combatSnapshot.copy(
                    player = combatSnapshot.player.copy(characterData = updatedTargetData),
                    enemy = combatSnapshot.enemy
                )
            }
        }
        return updatedSnapshot
    }
}
