package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.data.gamedata.LocalGameData
import com.unibo.mobile.data.models.dungeon.RoomImpl
import com.unibo.mobile.data.models.dungeon.RoomTypeCombatImpl
import com.unibo.mobile.data.models.dungeon.RoomTypeSafeImpl
import com.unibo.mobile.domain.model.dungeon.ChallengeRating
import com.unibo.mobile.domain.model.dungeon.Room
import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.strategy.DungeonCreationStrategy
import kotlin.collections.plus

class DungeonCreationStrategyDefault : DungeonCreationStrategy {

    private val maxCrIndex = ChallengeRating.entries.size - 1

    override fun generateRooms(winCounter: Int): List<Room> {
        val roomCount = winCounter + LocalGameData.BASE_ROOMS
        var dungeonRoomList: List<Room> = emptyList()
        for (roomIndex in 0 until roomCount) {
            val room = when {
                roomIndex == roomCount - 1 -> addFinalCombatRoom(roomIndex)
                roomIndex % 2 == 0 -> addSafeRoom(roomIndex)
                else -> addCombatRoomStandard(roomIndex)
            }
            dungeonRoomList = dungeonRoomList + room
        }
        return dungeonRoomList
    }

    private fun addSafeRoom(roomIndex: Int): Room {
        return RoomImpl(
            roomIndex = roomIndex,
            roomType = RoomTypeSafeImpl(
                availableActions = listOf(RoomAction.REST, RoomAction.LEVEL_UP),
                roomTypeId = "room_safe",
                displayName = "Safe Room"
            )
        )
    }

    private fun addCombatRoomStandard(roomIndex: Int): Room {
        return RoomImpl(
            roomIndex = roomIndex,
            roomType = RoomTypeCombatImpl(
                "room_combat",
                displayName = "Combat Room",
                minEnemyCr = crAt(roomIndex),
                maxTotalCr = crAt(roomIndex + 1),
                maxNumEnemies = LocalGameData.MAX_ENEMIES_PER_ROOM
            )
        )
    }

    private fun addFinalCombatRoom(roomIndex: Int): Room {
        val finalCr = crAt(roomIndex + LocalGameData.FINAL_ROOM_ADDED_CR)
        return RoomImpl(
            roomIndex = roomIndex,
            roomType = RoomTypeCombatImpl(
                "room_final",
                displayName = "Final Room",
                minEnemyCr = finalCr,
                maxTotalCr = finalCr,
                maxNumEnemies = LocalGameData.SINGLE_ENEMY_PER_ROOM
            )
        )
    }

    private fun crAt(index: Int): ChallengeRating =
        ChallengeRating.entries[index.coerceIn(0, maxCrIndex)]
}