package com.unibo.mobile.data.models.dungeon

import com.unibo.mobile.domain.model.dungeon.Room
import com.unibo.mobile.domain.model.dungeon.RoomType

class RoomImpl(
    override val roomIndex: Int,
    override val roomType: RoomType
) : Room {
}