package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.AbilityDamage
import com.unibo.mobile.domain.models.AbilityHeal
import com.unibo.mobile.domain.models.AbilityResult
import com.unibo.mobile.domain.models.Character
import com.unibo.mobile.domain.models.CharacterData

interface ApplyAbilityResultUseCase {
    fun invoke(target: Character, abilityResult: AbilityResult): CharacterData
}

class ApplyAbilityResultUseCaseImpl : ApplyAbilityResultUseCase {
    override fun invoke(target: Character, abilityResult: AbilityResult): CharacterData {
        val amount = if (abilityResult.ability.requiresHitRoll &&
            abilityResult.hitDiceRoll < target.characterData.armorClass) {
            0  // Mancato: 0 effetto
        } else {
            when (abilityResult.ability) {
                is AbilityDamage -> -abilityResult.effectDiceRoll
                is AbilityHeal -> abilityResult.effectDiceRoll
            }
        }
        return target.characterData.changeHealthPoints(amount)
    }
}