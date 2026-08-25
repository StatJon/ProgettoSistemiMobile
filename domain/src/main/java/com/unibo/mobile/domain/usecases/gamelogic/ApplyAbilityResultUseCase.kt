package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.AbilityResult
import com.unibo.mobile.domain.models.Character
import com.unibo.mobile.domain.models.CharacterData

interface ApplyAbilityResultUseCase {
    fun invoke(target: Character, abilityResult: AbilityResult): CharacterData
}