package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityDamage
import com.unibo.mobile.domain.models.ActionCost
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.DicesToRoll

interface DecideEnemyAbilityUseCase {
    fun invoke(enemy: CharacterEnemy): Ability
}

class DecideEnemyAbilityUseCaseImpl : DecideEnemyAbilityUseCase {
    override fun invoke(enemy: CharacterEnemy): Ability {
        val abilityList = enemy.characterData.abilityList.ifEmpty {
            listOf(
                AbilityDamage(
                    name = "Basic Attack",
                    level = 1,
                    isAoe = false,
                    actionCost = ActionCost.ACTION,
                    manaCost = 0,
                    dicesToRoll = DicesToRoll(1, 4),
                    requiresHitRoll = true
                )
            )
        }
        return abilityList.random()
    }
}