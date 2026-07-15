package com.unibo.mobile.data.usecases

import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.save.SaveSession
import com.unibo.mobile.domain.model.strategy.LevelUpStrategy
import com.unibo.mobile.domain.repositories.ApiRepository
import com.unibo.mobile.domain.usecases.RoomSafeUseCase

class RoomSafeUseCaseImpl(
    private val apiRepository: ApiRepository,
    private val levelUpStrategy: LevelUpStrategy
) : RoomSafeUseCase {

    override suspend fun applyRoomAction(
        saveSession: SaveSession,
        roomAction: RoomAction
    ): SaveSession {
        return when (roomAction) {
            RoomAction.REST -> applyRest(saveSession)
            RoomAction.LEVEL_UP -> applyLevelUp(saveSession)
        }
    }

    private fun applyRest(saveSession: SaveSession): SaveSession {
        val character = saveSession.playerCharacter
        val hpToAdd = character.characterMaxHp - character.characterHp
        val mpToAdd = character.characterMaxMp - character.characterMp
        return saveSession.updatePlayerCharacter(
            character
                .changeHp(hpToAdd)
                .changeMp(mpToAdd)
        )
    }

    private suspend fun applyLevelUp(saveSession: SaveSession): SaveSession {
        val character = saveSession.playerCharacter
        val level = (character.characterExp / 100) + 1

        val newAbilities = apiRepository.getAbilitiesPlayerCharacter(
            character.characterClass,
            level
        )

        val updatedCharacter = levelUpStrategy.applyLevelUp(character, newAbilities)
        return saveSession.updatePlayerCharacter(updatedCharacter)
    }
}
