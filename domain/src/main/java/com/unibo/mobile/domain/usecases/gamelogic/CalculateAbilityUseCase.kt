package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityResult
import com.unibo.mobile.domain.models.Character

interface CalculateAbilityUseCase {
    fun invoke(target: Character, ability: Ability): AbilityResult
}