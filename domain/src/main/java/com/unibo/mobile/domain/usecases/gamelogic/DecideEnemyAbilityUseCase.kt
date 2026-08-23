package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.CharacterEnemy

interface DecideEnemyAbilityUseCase {
    fun invoke(enemy: CharacterEnemy): Ability
}