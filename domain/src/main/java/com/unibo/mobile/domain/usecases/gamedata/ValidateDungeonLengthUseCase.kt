package com.unibo.mobile.domain.usecases.gamedata

import com.unibo.mobile.domain.repositories.GamedataRepository

interface ValidateDungeonLengthUseCase {
    suspend fun invoke(dungeonLength: Int): Int
}

class ValidateDungeonLengthUseCaseImpl(
    private val gamedataRepository: GamedataRepository
) : ValidateDungeonLengthUseCase {
    override suspend fun invoke(dungeonLength: Int): Int {
        return dungeonLength.coerceAtMost(gamedataRepository.getDungeonMaxLength())
    }

}