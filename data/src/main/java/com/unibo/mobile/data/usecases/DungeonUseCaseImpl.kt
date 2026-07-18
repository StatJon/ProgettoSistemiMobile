package com.unibo.mobile.data.usecases

import com.unibo.mobile.data.gamedata.LocalGameData
import com.unibo.mobile.data.models.dungeon.RoomImpl
import com.unibo.mobile.data.models.dungeon.DungeonImpl
import com.unibo.mobile.data.models.dungeon.RoomTypeCombatImpl
import com.unibo.mobile.data.models.save.PlayerCharacterImpl
import com.unibo.mobile.domain.model.dungeon.ChallengeRating
import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.strategy.DungeonCreationStrategy
import com.unibo.mobile.domain.usecases.DungeonUseCase

class DungeonUseCaseImpl(
    private val dungeonCreationStrategy: DungeonCreationStrategy
) : DungeonUseCase {
    override fun initDungeon(
        winCounter: Int,
        savedDungeon: Dungeon?
    ): Dungeon {
        if (savedDungeon != null) {
            return savedDungeon
        }
        return DungeonImpl(
            rooms = dungeonCreationStrategy.generateRooms(winCounter),
            currentRoomIndex = 0
        )
    }

    override fun advanceToNextRoom(dungeon: Dungeon): Dungeon {
        return dungeon.advanceRoomIndex()
    }

    override fun checkVictory(dungeon: Dungeon, currentRoomIndex: Int): Boolean {
        return currentRoomIndex >= dungeon.rooms.size
    }

    override fun checkDefeat(playerCharacter: PlayerCharacter): Boolean {
        return playerCharacter.characterHp <= 0
    }

    override fun createNewPlayerCharacter(playerClass: PlayerClass): PlayerCharacter {
        return PlayerCharacterImpl(
            characterClass = playerClass,
            characterHp = playerClass.constitution,
            characterMaxHp = playerClass.constitution,
            characterMp = playerClass.intelligence,
            characterMaxMp = playerClass.intelligence,
            characterExp = 0,
            characterLevel = 1,
            abilityList = LocalGameData.baseAbilities
        )
    }
}