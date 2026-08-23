package com.unibo.mobile.domain.usecases.api

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.repositories.AbilityRepository

interface FetchAbilityByNameUseCase {
    suspend fun invoke(abilityName: String): Ability
}

class FetchAbilityByNameUseCaseImpl(
    private val abilityRepository: AbilityRepository
) : FetchAbilityByNameUseCase {
    override suspend fun invoke(abilityName: String): Ability {
        return abilityRepository.getAbilityByName(abilityName) ?: error("Error: Ability not found")
    }
}