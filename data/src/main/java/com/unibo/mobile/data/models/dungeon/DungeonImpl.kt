package com.unibo.mobile.data.models.dungeon

import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.dungeon.Room

data class DungeonImpl(
    override val rooms: List<Room>,
    override val currentRoomIndex: Int
) : Dungeon {
}