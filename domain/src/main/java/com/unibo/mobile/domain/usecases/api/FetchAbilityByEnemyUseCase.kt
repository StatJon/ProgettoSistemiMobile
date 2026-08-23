package com.unibo.mobile.domain.usecases.api

import com.unibo.mobile.domain.models.Ability

interface FetchAbilityByEnemyUseCase {
    suspend fun invoke(enemyIndex: String): List<Ability>
}