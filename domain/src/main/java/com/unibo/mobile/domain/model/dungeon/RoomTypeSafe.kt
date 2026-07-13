package com.unibo.mobile.domain.model.dungeon

interface RoomTypeSafe : RoomType {
    override val isSafe: Boolean get() = true
    val availableActions: List<RoomAction>
}
