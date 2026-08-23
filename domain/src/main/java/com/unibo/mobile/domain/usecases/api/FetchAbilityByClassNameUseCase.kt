package com.unibo.mobile.domain.usecases.api

import com.unibo.mobile.domain.models.Ability

interface FetchAbilityByClassNameUseCase {
    suspend fun invoke(className : String): Ability
}