package com.unibo.mobile.domain.model.dungeon

interface RoomType {
    val roomTypeId: String
    val displayName: String
    val isSafe: Boolean
}