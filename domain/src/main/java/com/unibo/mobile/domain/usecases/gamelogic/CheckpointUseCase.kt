package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.SaveSession

interface CheckpointUseCase {
    suspend fun invoke(saveSession: SaveSession?): SaveSession
}

class CheckpointUseCaseImpl (
    private val levelUpUseCase: LevelUpUseCase
) : CheckpointUseCase {
    override suspend fun invoke(saveSession: SaveSession?): SaveSession {
        val dungeonIndex = saveSession?.dungeon?.dungeonIndex
            ?: throw IllegalStateException("ERROR: CheckpointUseCase - dungeon missing in SaveSession")
        val characterPlayer = saveSession.characterPlayer
        val updatedDungeon = saveSession.dungeon.copy(dungeonIndex = dungeonIndex + 1)
        val updatedCharacterPlayer = levelUpUseCase.invoke(characterPlayer)
        println("DEBUG: Updated Player AbilityList: ${updatedCharacterPlayer.characterData.abilityList}")

        return SaveSession(
            dungeon = updatedDungeon,
            characterPlayer = updatedCharacterPlayer
        )
    }
}