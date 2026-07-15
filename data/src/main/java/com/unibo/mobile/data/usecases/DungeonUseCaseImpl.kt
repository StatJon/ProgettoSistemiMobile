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
import com.unibo.mobile.domain.usecases.DungeonUseCase

class DungeonUseCaseImpl : DungeonUseCase {
    override fun initDungeon(
        victoryCount: Int,
        savedDungeon: Dungeon?
    ): Dungeon {
        if (savedDungeon != null) {
            return savedDungeon
        }

        val totalRooms = LocalGameData.BASE_ROOMS + victoryCount

        val rooms = (0 until totalRooms).map { index ->
            val isLastRoom = index == totalRooms - 1
            val crIndex = if (isLastRoom) {
                (index * 2).coerceAtMost(ChallengeRating.entries.size - 1)
            } else {
                index
            }
            val roomCr = ChallengeRating.entries[crIndex]

            RoomImpl(
                roomIndex = index,
                roomType = RoomTypeCombatImpl(
                    roomTypeId = "combat_$index",
                    displayName = "Room $index",
                    minEnemyCr = ChallengeRating.CR_0,
                    maxTotalCr = roomCr,
                    maxNumEnemies = LocalGameData.MAX_ENEMIES_PER_ROOM
                )
            )
        }

        return DungeonImpl(
            rooms = rooms,
            currentRoomIndex = 0
        )
    }

    override fun advanceToNextRoom(dungeon: Dungeon): Dungeon {
        return dungeon.advanceRoomIndex()
    }

    override fun checkVictory(dungeon: Dungeon): Boolean {
        return dungeon.currentRoomIndex >= dungeon.rooms.size
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
            abilityList = emptyList()
        )
    }
}