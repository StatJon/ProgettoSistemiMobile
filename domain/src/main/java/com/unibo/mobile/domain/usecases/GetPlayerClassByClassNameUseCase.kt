package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.repositories.PlayerClassRepository

interface GetPlayerClassByClassNameUseCase {
    suspend fun invoke(className: String): PlayerClass
}

class GetPlayerClassByClassNameUseCaseImpl(
   private val playerClassRepository: PlayerClassRepository,

    ) : GetPlayerClassByClassNameUseCase {
    override suspend fun invoke(className: String): PlayerClass {
        return playerClassRepository.getPlayerClassByName(className)
            ?: error("player class $className not found")
    }
}