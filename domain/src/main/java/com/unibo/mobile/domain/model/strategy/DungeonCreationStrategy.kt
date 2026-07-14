package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.dungeon.Room

interface DungeonCreationStrategy {
    fun generateRooms(victoryCount: Int): List<Room>
}