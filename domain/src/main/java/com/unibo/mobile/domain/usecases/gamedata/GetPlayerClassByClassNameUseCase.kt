package com.unibo.mobile.domain.usecases.gamedata

import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.repositories.GamedataRepository

interface GetPlayerClassByClassNameUseCase {
    suspend fun invoke(className: String): PlayerClass
}

class GetPlayerClassByClassNameUseCaseImpl(
    private val gamedataRepository: GamedataRepository,

    ) : GetPlayerClassByClassNameUseCase {
    override suspend fun invoke(className: String): PlayerClass {
        return gamedataRepository.getPlayerClassByName(className)
            ?: error("player class $className not found")
    }
}