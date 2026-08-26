package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.CharacterPlayer

interface ApplyPlayerAbilityCostUseCase {
    fun invoke(player: CharacterPlayer, ability: Ability) : CharacterPlayer
}