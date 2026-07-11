package com.unibo.mobile.domain.model.dungeon

abstract class RoomTypeSafe (
    override val roomTypeId: String,
    override val displayName: String,
    val availAction: List<RoomAction>
) : RoomType {
    override val isSafe: Boolean = true
}