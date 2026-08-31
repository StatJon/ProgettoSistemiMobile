package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityResult
import com.unibo.mobile.domain.models.Character
import com.unibo.mobile.domain.models.DiceRoller
import com.unibo.mobile.domain.models.DicesToRoll

interface CalculateAbilityResultUseCase {
    fun invoke(target: Character, ability: Ability): AbilityResult
}

class CalculateAbilityResultUseCaseImpl() : CalculateAbilityResultUseCase {
    override fun invoke(
        target: Character,
        ability: Ability
    ): AbilityResult {
        val hitDiceRoll = if (ability.requiresHitRoll) {
            DiceRoller().invoke(
                dicesToRoll = DicesToRoll(
                    diceNumber = 1,
                    diceFaces = 20
                )
            )
        } else {
            99
        }
        val effectDiceRoll = DiceRoller().invoke(
            dicesToRoll = DicesToRoll(
                diceNumber = ability.dicesToRoll.diceNumber,
                diceFaces = ability.dicesToRoll.diceFaces
            )
        )
        return AbilityResult(
            target = target,
            ability = ability,
            effectDiceRoll = effectDiceRoll,
            hitDiceRoll = hitDiceRoll
        )
    }

}