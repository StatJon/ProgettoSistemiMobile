package com.unibo.mobile.domain.usecases.gamedata

import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.repositories.GamedataRepository

interface GetAllPlayerClassesUseCase {
    suspend fun invoke(): List<PlayerClass>
}

class GetAllPlayerClassesUseCaseImpl(
    private val gamedataRepository: GamedataRepository
) : GetAllPlayerClassesUseCase{
    override suspend fun invoke(): List<PlayerClass> {
        return gamedataRepository.getAllPlayerClasses()
    }
}