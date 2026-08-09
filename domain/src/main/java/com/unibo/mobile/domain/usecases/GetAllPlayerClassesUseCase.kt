package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.repositories.PlayerClassRepository

interface GetAllPlayerClassesUseCase {
    suspend fun invoke(): List<PlayerClass>
}

class GetAllPlayerClassesUseCaseImpl(
    private val playerClassRepository: PlayerClassRepository
) : GetAllPlayerClassesUseCase{
    override suspend fun invoke(): List<PlayerClass> {
        return playerClassRepository.getAllPlayerClasses()
    }
}