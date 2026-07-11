package com.unibo.mobile.domain.model.dungeon

abstract class RoomTypeCombat(
    override val roomTypeId: String,
    override val displayName: String,
    val challengeRating: Float
) : RoomType {
    override val isSafe: Boolean = false
}
