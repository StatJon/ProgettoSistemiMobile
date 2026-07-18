package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.data.gamedata.LocalGameData
import com.unibo.mobile.data.models.dungeon.RoomTypeCombatImpl
import com.unibo.mobile.data.models.dungeon.RoomTypeSafeImpl
import com.unibo.mobile.domain.model.dungeon.ChallengeRating
import com.unibo.mobile.domain.model.dungeon.Room
import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.strategy.DungeonCreationStrategy
import kotlin.collections.plus

class DungeonCreationStrategyDefault : DungeonCreationStrategy {
    override fun generateRooms(winCounter: Int): List<Room> {
        val roomCount = winCounter + LocalGameData.BASE_ROOMS
        var dungeonRoomList: List<Room> = emptyList()
        for (roomIndex in 1..roomCount) {
            when (roomIndex % 2) {
                0 -> (if (roomIndex == roomCount)
                    dungeonRoomList + addFinalCombatRoom(roomIndex)
                else dungeonRoomList + addCombatRoomStandard(roomIndex))

                1 -> dungeonRoomList + addSafeRoom(roomIndex)
            }
        }
        return dungeonRoomList
    }

    private fun addCombatRoomStandard(roomIndex: Int): RoomTypeCombatImpl {
        return RoomTypeCombatImpl(
            "room_combat",
            displayName = "Combat Room",
            minEnemyCr = ChallengeRating.entries[roomIndex],
            maxTotalCr = ChallengeRating.entries[roomIndex + 1],
            maxNumEnemies = LocalGameData.MAX_ENEMIES_PER_ROOM
        )
    }

    private fun addSafeRoom(roomIndex: Int): RoomTypeSafeImpl {
        return RoomTypeSafeImpl(
            availableActions = listOf(RoomAction.REST, RoomAction.LEVEL_UP),
            roomTypeId = "room_safe",
            displayName = "Safe Room"
        )
    }

    private fun addFinalCombatRoom(roomIndex: Int): RoomTypeCombatImpl {
        return RoomTypeCombatImpl(
            "room_final",
            displayName = "Final Room",
            minEnemyCr = ChallengeRating.entries[roomIndex * 2],
            maxTotalCr = ChallengeRating.entries[roomIndex * 2],
            maxNumEnemies = LocalGameData.SINGLE_ENEMY_PER_ROOM
        )
    }

}